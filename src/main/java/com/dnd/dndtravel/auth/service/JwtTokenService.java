package com.dnd.dndtravel.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnd.dndtravel.auth.domain.RefreshToken;
import com.dnd.dndtravel.auth.exception.RefreshTokenInvalidException;
import com.dnd.dndtravel.auth.repository.RefreshTokenRepository;
import com.dnd.dndtravel.auth.service.dto.response.TokenResponse;
import com.dnd.dndtravel.auth.service.dto.response.ReissueTokenResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtTokenService {
	private final JwtProvider jwtProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	@Transactional
	public TokenResponse generateTokens(Long memberId, String appleRefreshToken) {
		RefreshToken refreshToken = refreshTokenRepository.findByMemberId(memberId);

		// 리프레시 토큰이 없는경우
		if (refreshToken == null) {
			String newRefreshToken = jwtProvider.refreshToken();
			refreshTokenRepository.save(RefreshToken.of(memberId, newRefreshToken)); // refreshToken은 DB에 저장
			return new TokenResponse(jwtProvider.accessToken(memberId), newRefreshToken, appleRefreshToken);
		}
		
		// 리프레시 토큰이 만료됐으면 재발급 받으라고 멘트줌
		if (refreshToken.isExpire()) {
			return null;
		}
		
		// 리프레시 토큰이 DB에 존재하고 유효한경우
		refreshTokenRepository.delete(refreshToken);
		String newRefreshToken = jwtProvider.refreshToken();
		refreshTokenRepository.save(RefreshToken.of(refreshToken.getMemberId(), newRefreshToken));
		return new TokenResponse(jwtProvider.accessToken(memberId), newRefreshToken, appleRefreshToken);
	}

	@Transactional
	public ReissueTokenResponse reIssue(String token) {
		//validation
		RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token).orElseThrow(() -> new RefreshTokenInvalidException(token));

		//RTR
		refreshTokenRepository.delete(refreshToken);
		String newRefreshToken = jwtProvider.refreshToken();
		refreshTokenRepository.save(RefreshToken.of(refreshToken.getMemberId(), newRefreshToken));
		return new ReissueTokenResponse(jwtProvider.accessToken(refreshToken.getMemberId()), newRefreshToken);
	}
}
