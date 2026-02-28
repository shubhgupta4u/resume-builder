package com.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InternalTokenGenerator {

    private final JwtEncoder jwtEncoder;
    @Value("${auth.issuer}")
    private String issuer;

    @Value("${auth.token.expiry}")
    private int expiryDuration;

    public String generateSystemToken() {

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiryDuration)) // 10 min
                .subject("internal-system")
                .audience(List.of("admin-service"))
                .claim("scope", List.of("client:internal-read"))
                .build();

        JwsHeader headers = JwsHeader.with(() -> "RS256").build();

        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(headers, claims));

        return jwt.getTokenValue();
    }
}