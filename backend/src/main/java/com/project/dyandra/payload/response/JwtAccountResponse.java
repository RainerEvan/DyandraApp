package com.project.dyandra.payload.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAccountResponse {
    private String accessToken;
    private Instant expirationDate;
    private String userId;
    private Boolean isActive;
    private String role;
}