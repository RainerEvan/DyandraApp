package com.project.dyandra.payload.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
    private UUID connectionId;
    private UUID sourcePathId;
    private String query;
    private String title;
    private String reportConfig;
}
