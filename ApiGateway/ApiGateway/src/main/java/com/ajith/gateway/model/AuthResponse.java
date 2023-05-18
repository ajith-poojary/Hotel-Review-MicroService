package com.ajith.gateway.model;

import lombok.*;

import java.util.Collection;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String userId;

    private String accessToken;

    private String refreshToken;

    private  long expiresAt;

    private Collection<String> authorities;
}
