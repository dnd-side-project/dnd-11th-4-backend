package com.dnd.dndtravel.auth.apple.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/*
id_token 검증
애플 서버에서 jwk 리스트를 받아와 이를 일급 컬렉션 형태로 관리
 */
@Getter
public class ApplePublicKey {
    private final String kty; //Key Type(RSA or EC(Elliptic Curve))

    private final String kid; //key ID

    private final String use; //퍼블릭 키가 어떤 용도로 사용되는지 명시 ("sig"(signature) or "enc"(encryption))

    private final String alg; //어떤 알고리즘을 사용하는지

    private final String n; //RSA modulus

    private final String e; //RSA public exponent

    public boolean isSameAlg(final String alg) {
        return this.alg.equals(alg);
    }

    public boolean isSameKid(final String kid) {
        return this.kid.equals(kid);
    }

    @JsonCreator
    public ApplePublicKey(@JsonProperty("kty") final String kty,
                          @JsonProperty("kid") final String kid,
                          @JsonProperty("use") final String use,
                          @JsonProperty("alg") final String alg,
                          @JsonProperty("n") final String n,
                          @JsonProperty("e") final String e) {
        this.kty = kty;
        this.kid = kid;
        this.use = use;
        this.alg = alg;
        this.n = n;
        this.e = e;
    }
}
