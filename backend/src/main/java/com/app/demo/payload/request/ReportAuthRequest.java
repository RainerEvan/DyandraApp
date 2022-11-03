package com.app.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportAuthRequest {
    private String clientId;
    private String reportId;
}
