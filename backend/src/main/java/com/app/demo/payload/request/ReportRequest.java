package com.app.demo.payload.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportRequest {
    private UUID connectionId;
    private UUID sourcePathId;
    private String query;
    private String title;
    private String reportConfig;
}
