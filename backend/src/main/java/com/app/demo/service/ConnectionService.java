package com.app.demo.service;

import java.time.OffsetDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.demo.exception.AbstractGraphQLException;
import com.app.demo.model.Applications;
import com.app.demo.model.Connections;
import com.app.demo.model.Methods;
import com.app.demo.payload.request.ConnectionRequest;
import com.app.demo.repository.ApplicationRepository;
import com.app.demo.repository.ConnectionRepository;
import com.app.demo.repository.MethodRepository;

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
            .orElseThrow(() -> new AbstractGraphQLException("Application with current id cannot be found"));

        Methods method = methodRepository.findByName(connectionRequest.getMethodName())
            .orElseThrow(() -> new AbstractGraphQLException("Method with current name cannot be found"));

        Connections connection = new Connections();
        connection.setApplication(application);
        connection.setMethod(method);
        connection.setCreatedAt(OffsetDateTime.now());

        return connectionRepository.save(connection);
    }
}
