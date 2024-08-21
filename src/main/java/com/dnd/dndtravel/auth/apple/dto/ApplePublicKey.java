package com.dnd.dndtravel.auth.apple.dto;

import lombok.Getter;

/**
 * @param kty Key Type(RSA or EC(Elliptic Curve))
 * @param kid key ID
 * @param use 퍼블릭 키가 어떤 용도로 사용되는지 명시 ("sig"(signature) or "enc"(encryption))
 * @param alg 어떤 알고리즘을 사용하는지
 * @param n RSA modulus
 * @param e RSA public exponent */ /*
id_token 검증
애플 서버에서 jwk 리스트를 받아와 이를 일급 컬렉션 형태로 관리
 */
public record ApplePublicKey(
    String kty,
    String kid,
    String use,
    String alg,
    String n,
    String e
) {
    public boolean isSameAlg(final String alg) {
        return this.alg.equals(alg);
    }

    public boolean isSameKid(final String kid) {
        return this.kid.equals(kid);
    }
}
