package com.dnd.dndtravel.config;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dnd.dndtravel.auth.service.JwtProvider;
import com.dnd.dndtravel.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class WebMvcConfig implements WebMvcConfigurer {

	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new AuthResolver(memberRepository, jwtProvider));
	}
}
