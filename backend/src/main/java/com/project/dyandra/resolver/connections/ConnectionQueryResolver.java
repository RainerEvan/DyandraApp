package com.project.dyandra.resolver.connections;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.dyandra.model.Connections;
import com.project.dyandra.service.ConnectionService;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ConnectionQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final ConnectionService connectionService;

    public List<Connections> getAllConnections(){
        return connectionService.getAllConnections();
    }
}
