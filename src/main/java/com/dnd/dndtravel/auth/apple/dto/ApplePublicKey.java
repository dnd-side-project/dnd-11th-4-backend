package com.dnd.dndtravel.auth.apple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/*
id_token 검증
애플 서버에서 jwk 리스트를 받아와 이를 일급 컬렉션 형태로 관리
 */
@Getter
public record ApplePublicKey(String kty, String kid, String use, String alg,
                             String n, String e) {
    public boolean isSameAlg(final String alg) {
        return this.alg.equals(alg);
    }

    public boolean isSameKid(final String kid) {
        return this.kid.equals(kid);
    }

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
