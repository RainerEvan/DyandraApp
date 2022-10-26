package com.app.demo.payload.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SourcePathRequest {
    private UUID connectionId;
    private String path;
    private String username;
    private String password;
}
