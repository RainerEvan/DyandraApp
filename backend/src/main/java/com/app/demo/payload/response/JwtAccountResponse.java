package com.app.demo.payload.response;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAccountResponse {
    private String accessToken;
    private Instant expirationDate;
    private String username;
    private Boolean isActive;
    private List<String> role;
}