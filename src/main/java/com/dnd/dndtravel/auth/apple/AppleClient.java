package com.dnd.dndtravel.auth.apple;

import com.dnd.dndtravel.auth.apple.dto.ApplePublicKeys;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "apple-public-key", url = "https://appleid.apple.com")
public interface AppleClient {
    @GetMapping("/auth/keys")
    ApplePublicKeys getApplePublicKeys();
}
