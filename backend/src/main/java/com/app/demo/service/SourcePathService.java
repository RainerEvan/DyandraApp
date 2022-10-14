package com.app.demo.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.demo.model.Connections;
import com.app.demo.model.SourcePaths;
import com.app.demo.payload.request.SourcePathRequest;
import com.app.demo.repository.ConnectionRepository;
import com.app.demo.repository.SourcePathRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SourcePathService {
    
    @Autowired
    private final SourcePathRepository sourcePathRepository;
    @Autowired
    private final ConnectionRepository connectionRepository;

    public List<SourcePaths> getAllSourcePathsForConnection(UUID connectionId){
        Connections connection = connectionRepository.findById(connectionId)
            .orElseThrow(() -> new IllegalStateException("Connection with current id cannot be found"));

        return sourcePathRepository.findAllByConnection(connection);
    }

    public SourcePaths addSourcePath(SourcePathRequest sourcePathRequest){
        Connections connection = connectionRepository.findById(sourcePathRequest.getConnectionId())
            .orElseThrow(() -> new IllegalStateException("Connection with current id cannot be found"));

        SourcePaths sourcePath = new SourcePaths();
        sourcePath.setConnection(connection);
        sourcePath.setPath(sourcePathRequest.getPath());
        sourcePath.setCreatedAt(OffsetDateTime.now());

        return sourcePathRepository.save(sourcePath);
    }
}
