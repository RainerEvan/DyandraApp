package com.project.dyandra.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dyandra.model.Applications;
import com.project.dyandra.model.Connections;
import com.project.dyandra.model.Methods;
import com.project.dyandra.payload.request.ConnectionRequest;
import com.project.dyandra.repository.ApplicationRepository;
import com.project.dyandra.repository.ConnectionRepository;
import com.project.dyandra.repository.MethodRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConnectionService {
    
    @Autowired
    private final ConnectionRepository connectionRepository;
    @Autowired
    private final ApplicationRepository applicationRepository;
    @Autowired
    private final MethodRepository methodRepository;

    @Transactional
    public List<Connections> getAllConnections(){
        return connectionRepository.findAll();
    }
    
    @Transactional
    public Connections addConnection(ConnectionRequest connectionRequest){
        Applications application = applicationRepository.findById(connectionRequest.getApplicationId())
            .orElseThrow(() -> new IllegalStateException("Application with current id cannot be found"));

        Methods method = methodRepository.findByName(connectionRequest.getMethodName())
            .orElseThrow(() -> new IllegalStateException("Method with current name cannot be found"));

        Connections connection = new Connections();
        connection.setApplication(application);
        connection.setMethod(method);
        connection.setName(connectionRequest.getName());
        connection.setCreatedAt(OffsetDateTime.now());

        return connectionRepository.save(connection);
    }

    @Transactional
    public void deleteConnection(UUID connectionId){
        Connections connection = connectionRepository.findById(connectionId)
            .orElseThrow(() -> new IllegalStateException("Connection with current id cannot be found: "+connectionId));

        connectionRepository.delete(connection);
    }
}
