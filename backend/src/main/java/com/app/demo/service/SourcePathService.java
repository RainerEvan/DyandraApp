package com.app.demo.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.demo.exception.AbstractGraphQLException;
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
        sourcePath.setPath(sourcePathRequest.getPath());
        sourcePath.setCreatedAt(OffsetDateTime.now());

        return sourcePathRepository.save(sourcePath);
    }

    @Transactional
    public void deleteSourcePath(UUID sourcePathId){
        SourcePaths sourcePath = sourcePathRepository.findById(sourcePathId)
            .orElseThrow(() -> new IllegalStateException("Source path with current id cannot be found: "+sourcePathId));

        sourcePathRepository.delete(sourcePath);
    }
}
