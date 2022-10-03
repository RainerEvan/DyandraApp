package com.app.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportRequest {
    private String title;
    private String report;
}
