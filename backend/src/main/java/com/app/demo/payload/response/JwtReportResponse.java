package com.app.demo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtReportResponse {
    private String accessToken;
    private String refreshToken;
    private String reportTitle;
}
