package com.app.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.demo.model.Applications;
import com.app.demo.payload.request.ApplicationRequest;
import com.app.demo.repository.ApplicationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApplicationService {
    
    @Autowired
    private final ApplicationRepository applicationRepository;

    public List<Applications> getAllApplications(){
        return applicationRepository.findAll();
    }

    public Applications addApplication(ApplicationRequest applicationRequest){
        String clientToken = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();

        Applications application = new Applications();
        application.setName(applicationRequest.getName());
        application.setCode(applicationRequest.getCode());
        application.setClientToken(clientToken);

        return applicationRepository.save(application);
    }
}
