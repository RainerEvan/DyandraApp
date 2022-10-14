package com.app.demo.payload.request;

import java.util.UUID;

import com.app.demo.data.EMethod;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConnectionRequest {
    private UUID applicationId;
    private EMethod methodName;
}
