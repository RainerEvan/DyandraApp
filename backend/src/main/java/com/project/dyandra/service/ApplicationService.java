package com.project.dyandra.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dyandra.model.Applications;
import com.project.dyandra.payload.request.ApplicationRequest;
import com.project.dyandra.repository.ApplicationRepository;

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
    public Applications getApplication(String clientId){
        return applicationRepository.findByClientId(clientId)
            .orElseThrow(() -> new IllegalStateException("Application with current cliend id cannot be found"));
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
