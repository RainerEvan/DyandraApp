package com.project.dyandra.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUIDMRequest {
    private String userId;
    private String password;
    private String hostName;
}
