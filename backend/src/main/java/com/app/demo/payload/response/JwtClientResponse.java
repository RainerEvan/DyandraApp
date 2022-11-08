package com.app.demo.payload.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtClientResponse {
    private String accessToken;
    private Instant expirationDate;
    private String application;
}
