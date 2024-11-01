package com.dnd.dndtravel.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.dnd.dndtravel.auth.service.dto.response.AppleTokenResponse;
import com.dnd.dndtravel.auth.service.dto.response.TokenResponse;
import com.dnd.dndtravel.member.domain.Member;
import com.dnd.dndtravel.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthService {
	private final AppleOAuthService appleOAuthService;
	private final MemberService memberService;
	private final JwtTokenService jwtTokenService;

	public Optional<TokenResponse> processAppleLogin(String authorizationCode, String selectedColor) {
		AppleTokenResponse appleToken = appleOAuthService.get(authorizationCode);

		Member member = memberService.saveMember(
			appleToken.email(),
			appleToken.sub(),
			selectedColor
		);

		return Optional.ofNullable(
			jwtTokenService.generateTokens(member.getId(), appleToken.appleRefreshToken())
		);
	}

	public void processAppleRevoke(String refreshToken, long memberId) {
		appleOAuthService.revoke(refreshToken);
		memberService.withdrawMember(memberId);
	}
}
