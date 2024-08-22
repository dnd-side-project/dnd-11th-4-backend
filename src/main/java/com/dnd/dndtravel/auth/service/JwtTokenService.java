package com.dnd.dndtravel.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.dndtravel.auth.domain.RefreshToken;
import com.dnd.dndtravel.auth.repository.RefreshTokenRepository;
import com.dnd.dndtravel.auth.service.dto.response.TokenResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtTokenService {
	private final JwtProvider jwtProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	// todo 현재 refreshtoken하나로 계속해서 발급해주는 모델인데, 이는 리프레쉬 탈취시 보안위협있음. 추후 개선 필요
	@Transactional
	public TokenResponse generateTokens(Long memberId) {
		RefreshToken refreshToken = refreshTokenRepository.findByMemberId(memberId);

		if (refreshToken == null) {
			String newRefreshToken = jwtProvider.refreshToken(memberId);
			refreshTokenRepository.save(RefreshToken.of(memberId, newRefreshToken)); // refreshToken은 DB에 저장
			return new TokenResponse(jwtProvider.accessToken(memberId), newRefreshToken);
		} else if (refreshToken.isExpire()) {
			return null;
		}

		return new TokenResponse(jwtProvider.accessToken(memberId), null);
	}
}
