package com.project.dyandra.payload.request;

import java.util.UUID;

import com.project.dyandra.data.EMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionRequest {
    private UUID applicationId;
    private EMethod methodName;
    private String name;
}
