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
	public TokenResponse generateTokens(Long memberId) {
		RefreshToken refreshToken = refreshTokenRepository.findByMemberId(memberId);

		if (refreshToken == null) {
			String newRefreshToken = jwtProvider.refreshToken();
			refreshTokenRepository.save(RefreshToken.of(memberId, newRefreshToken)); // refreshToken은 DB에 저장
			return new TokenResponse(jwtProvider.accessToken(memberId), newRefreshToken);
		} else if (refreshToken.isExpire()) {
			return null;
		}

		return new TokenResponse(jwtProvider.accessToken(memberId), null);
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
