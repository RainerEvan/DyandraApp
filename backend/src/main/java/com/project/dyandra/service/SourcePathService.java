package com.project.dyandra.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dyandra.exception.AbstractGraphQLException;
import com.project.dyandra.model.Connections;
import com.project.dyandra.model.SourcePaths;
import com.project.dyandra.payload.request.SourcePathRequest;
import com.project.dyandra.repository.ConnectionRepository;
import com.project.dyandra.repository.SourcePathRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SourcePathService {
    
    @Autowired
    private final SourcePathRepository sourcePathRepository;
    @Autowired
    private final ConnectionRepository connectionRepository;

    @Transactional
    public List<SourcePaths> getAllSourcePaths(){
        return sourcePathRepository.findAll();
    }

    @Transactional
    public List<SourcePaths> getAllSourcePathsForConnection(UUID connectionId){
        Connections connection = connectionRepository.findById(connectionId)
            .orElseThrow(() -> new AbstractGraphQLException("Connection with current id cannot be found"));

        return sourcePathRepository.findAllByConnection(connection);
    }

    @Transactional
    public SourcePaths addSourcePath(SourcePathRequest sourcePathRequest){
        Connections connection = connectionRepository.findById(sourcePathRequest.getConnectionId())
            .orElseThrow(() -> new IllegalStateException("Connection with current id cannot be found"));

        SourcePaths sourcePath = new SourcePaths();
        sourcePath.setConnection(connection);
        sourcePath.setName(sourcePathRequest.getName());
        sourcePath.setPath(sourcePathRequest.getPath());
        sourcePath.setUsername(sourcePathRequest.getUsername());
        sourcePath.setPassword(sourcePathRequest.getPassword());
        sourcePath.setCreatedAt(OffsetDateTime.now());

        return sourcePathRepository.save(sourcePath);
    }

    @Transactional
    public SourcePaths editSourcePath(UUID sourcePathId, SourcePathRequest sourcePathRequest){
        SourcePaths sourcePath = sourcePathRepository.findById(sourcePathId)
            .orElseThrow(() -> new IllegalStateException("Source path with current id cannot be found: "+sourcePathId));

        sourcePath.setPath(sourcePathRequest.getPath());
        sourcePath.setUsername(sourcePathRequest.getUsername());
        sourcePath.setPassword(sourcePathRequest.getPassword());
        sourcePath.setUpdatedAt(OffsetDateTime.now());

        return sourcePathRepository.save(sourcePath);
    }

    @Transactional
    public void deleteSourcePath(UUID sourcePathId){
        SourcePaths sourcePath = sourcePathRepository.findById(sourcePathId)
            .orElseThrow(() -> new IllegalStateException("Source path with current id cannot be found: "+sourcePathId));

        sourcePathRepository.delete(sourcePath);
    }
}
