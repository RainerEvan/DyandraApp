package com.project.dyandra.resolver.methods;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.dyandra.model.Methods;
import com.project.dyandra.service.MethodService;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MethodQueryResolver implements GraphQLQueryResolver{
    
    @Autowired
    private final MethodService methodService;

    public List<Methods> getAllMethods(){
        return methodService.getAllMethods();
    }
}
