package com.project.dyandra.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dyandra.model.Methods;
import com.project.dyandra.repository.MethodRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MethodService {
    
    @Autowired
    private final MethodRepository methodRepository;

    public List<Methods> getAllMethods(){
        return methodRepository.findAll();
    }
}
