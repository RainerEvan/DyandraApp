package com.app.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.demo.model.Methods;
import com.app.demo.repository.MethodRepository;

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
