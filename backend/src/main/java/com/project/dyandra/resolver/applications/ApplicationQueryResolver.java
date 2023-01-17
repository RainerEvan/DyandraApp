package com.project.dyandra.resolver.applications;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.dyandra.model.Applications;
import com.project.dyandra.service.ApplicationService;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ApplicationQueryResolver implements GraphQLQueryResolver {
    
    @Autowired
    private final ApplicationService applicationService;

    public List<Applications> getAllApplications(){
        return applicationService.getAllApplications();
    }

}
