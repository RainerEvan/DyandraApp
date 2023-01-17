package com.project.dyandra.resolver.sourcepaths;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.dyandra.model.SourcePaths;
import com.project.dyandra.service.SourcePathService;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SourcePathQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final SourcePathService sourcePathService;

    public List<SourcePaths> getAllSourcePaths(){
        return sourcePathService.getAllSourcePaths();
    }

    public List<SourcePaths> getAllSourcePathsForConnection(UUID connectionId){
        return sourcePathService.getAllSourcePathsForConnection(connectionId);
    }
}
