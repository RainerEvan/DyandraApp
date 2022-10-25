package com.app.demo.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

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

    @Transactional
    public List<Applications> getAllApplications(){
        return applicationRepository.findAll();
    }

    @Transactional
    public Applications addApplication(ApplicationRequest applicationRequest){
        String clientId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();

        Applications application = new Applications();
        application.setName(applicationRequest.getName());
        application.setCode(applicationRequest.getCode());
        application.setClientId(clientId);

        return applicationRepository.save(application);
    }

    @Transactional
    public void deleteApplication(UUID applicationId){
        Applications application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new IllegalStateException("Application with current id cannot be found: "+applicationId));

        applicationRepository.delete(application);
    }
}
