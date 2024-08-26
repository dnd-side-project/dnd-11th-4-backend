package com.dnd.dndtravel.config;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.dnd.dndtravel.auth.service.JwtProvider;
import com.dnd.dndtravel.member.domain.Member;
import com.dnd.dndtravel.member.repository.MemberRepository;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String MEMBER_ID_CLAIM = "memberId";
	private static final String BEARER_PREFIX = "Bearer";
	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(AuthenticationMember.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

		// 헤더 자체가 비어있는 경우
		String header = webRequest.getHeader(AUTHORIZATION_HEADER);
		if (header == null || header.isEmpty()) {
			throw new RuntimeException("토큰이 없음");
		}

		String[] splitHeaders = header.split(" ");

		//<Bearer> <token> 형식이 아닌경우
		if (splitHeaders.length != 2 || !BEARER_PREFIX.equals(splitHeaders[0])) {
			throw new RuntimeException("유효하지 않은 토큰 형식");
		}

		//토큰 까봐서 만료,조작여부 확인
		Claims accessClaim = jwtProvider.parseClaims(splitHeaders[1]);

		//토큰에 심었던 user 식별자값 유효성 확인
		Member member = memberRepository.findById((Long)accessClaim.get(MEMBER_ID_CLAIM))
			.orElseThrow(() -> new RuntimeException("유효하지 않은 토큰 값"));

		// 컨트롤러 파라미터로 반환
		return new AuthenticationMember(member.getId());
	}
}
