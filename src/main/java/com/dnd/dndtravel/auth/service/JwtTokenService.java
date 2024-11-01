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
			return createNewTokens(memberId, appleRefreshToken);
		}
		
		// 리프레시 토큰이 만료됐으면 재발급
		if (refreshToken.isExpire()) {
			return null;
		}
		
		// 리프레시 토큰이 DB에 존재하고 유효한경우
		return rotateTokens(memberId, appleRefreshToken, refreshToken);
	}

	@Transactional
	public ReissueTokenResponse reIssue(String token) {
		RefreshToken oldRefreshToken = refreshTokenRepository.findByRefreshToken(token).orElseThrow(() -> new RefreshTokenInvalidException(token));
		String newRefreshToken = rotateRefreshToken(oldRefreshToken);
		String newAccessToken = jwtProvider.accessToken(oldRefreshToken.getMemberId());

		return new ReissueTokenResponse(newAccessToken, newRefreshToken);
	}

	private String rotateRefreshToken(RefreshToken oldRefreshToken) {
		refreshTokenRepository.delete(oldRefreshToken);
		String newRefreshToken = jwtProvider.refreshToken();
		refreshTokenRepository.save(RefreshToken.of(oldRefreshToken.getMemberId(), newRefreshToken));
		return newRefreshToken;
	}

	private TokenResponse rotateTokens(Long memberId, String appleRefreshToken, RefreshToken refreshToken) {
		String newRefreshToken = rotateRefreshToken(refreshToken);

		return new TokenResponse(jwtProvider.accessToken(memberId), newRefreshToken, appleRefreshToken);
	}

	private TokenResponse createNewTokens(Long memberId, String appleRefreshToken) {
		String newRefreshToken = jwtProvider.refreshToken();
		refreshTokenRepository.save(RefreshToken.of(memberId, newRefreshToken)); // refreshToken은 DB에 저장

		return new TokenResponse(jwtProvider.accessToken(memberId), newRefreshToken, appleRefreshToken);
	}
}
