package com.project.dyandra.payload.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private String userId;
    private String password;
    private String hostName;
    private UUID roleId;
}
