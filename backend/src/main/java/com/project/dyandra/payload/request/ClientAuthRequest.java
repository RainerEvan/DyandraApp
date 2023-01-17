package com.project.dyandra.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientAuthRequest {
    private String clientId;
    private String reportId;
}
