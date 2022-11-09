package com.app.demo.payload.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private String username;
    private String password;
    private UUID roleId;
}
