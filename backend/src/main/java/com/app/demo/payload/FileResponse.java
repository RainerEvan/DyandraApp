package com.app.demo.payload;

import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    @Lob
    private String fileBase64;
    private String fileName;
    private String fileType;
    private Long fileSize;
}
