package com.app.demo.payload.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourcePathRequest {
    private UUID connectionId;
    private String name;
    private String path;
    private String username;
    private String password;
}
