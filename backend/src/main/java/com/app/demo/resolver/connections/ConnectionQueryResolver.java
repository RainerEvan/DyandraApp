package com.app.demo.resolver.connections;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.demo.model.Connections;
import com.app.demo.service.ConnectionService;

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
