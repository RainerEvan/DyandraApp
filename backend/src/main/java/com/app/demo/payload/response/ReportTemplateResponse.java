package com.app.demo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportTemplateResponse {
    private String title;
    private String template;
}
