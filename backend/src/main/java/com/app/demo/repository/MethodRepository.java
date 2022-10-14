package com.app.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.demo.data.EMethod;
import com.app.demo.model.Methods;

@Repository
public interface MethodRepository extends JpaRepository<Methods, UUID> {
    Optional<Methods> findByName(EMethod name);
    
}
