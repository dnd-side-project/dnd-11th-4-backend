package com.dnd.dndtravel.auth.apple;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Map;

/*
JWK 리스트 조회
헤더 부분을 디코딩하여 일치하는 jwk를 찾을 alg, kid 값을 얻고, id_token Claim 추출
 */
@RequiredArgsConstructor
@Component
public class AppleTokenParser {
    private static final String IDENTITY_TOKEN_VALUE_DELIMITER = "\\.";
    private static final int HEADER_INDEX = 0;
    private final ObjectMapper objectMapper;

    public Map<String, String> parseHeader(String appleToken) {
        try {
            final String decodedHeader = appleToken.split(IDENTITY_TOKEN_VALUE_DELIMITER)[HEADER_INDEX];
            return objectMapper.readValue(decodedHeader, Map.class);
        } catch (JsonMappingException e) {
            throw new RuntimeException("appleToken 값이 jwt 형식인지, 값이 정상적인지 확인해주세요.");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("디코드된 헤더를 Map 형태로 분류할 수 없습니다. 헤더를 확인해주세요.");
        }
    }

    public Claims extractClaims(String appleToken, PublicKey publicKey) {
        try {
            return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(appleToken)
                .getPayload();
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("지원되지 않는 jwt 타입");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("비어있는 jwt");
        } catch (JwtException e) {
            throw new JwtException("jwt 검증 or 분석 오류");
        }
    }
}
