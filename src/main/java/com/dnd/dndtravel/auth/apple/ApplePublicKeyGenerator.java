package com.dnd.dndtravel.auth.apple;

import com.dnd.dndtravel.auth.apple.dto.ApplePublicKey;
import com.dnd.dndtravel.auth.apple.dto.ApplePublicKeys;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@Component
public class ApplePublicKeyGenerator {
    private static final String SIGN_ALGORITHM_HEADER = "alg";
    private static final String KEY_ID_HEADER = "kid";
    private static final int POSITIVE_SIGN_NUMBER = 1;

    public PublicKey generate(Map<String, String> headers, ApplePublicKeys publicKeys) {
        ApplePublicKey applePublicKey = publicKeys.getMatchingKey(
                headers.get(SIGN_ALGORITHM_HEADER),
                headers.get(KEY_ID_HEADER)
        );
        return generatePublicKey(applePublicKey);
    }

    private PublicKey generatePublicKey(ApplePublicKey applePublicKey) {
        byte[] nBytes = Base64.getUrlDecoder().decode(applePublicKey.n());
        byte[] eBytes = Base64.getUrlDecoder().decode(applePublicKey.e());

        BigInteger n = new BigInteger(POSITIVE_SIGN_NUMBER, nBytes);
        BigInteger e = new BigInteger(POSITIVE_SIGN_NUMBER, eBytes);
        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(n, e);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(applePublicKey.kty());
            return keyFactory.generatePublic(rsaPublicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw new RuntimeException("잘못된 애플 키");
        }
    }
}
