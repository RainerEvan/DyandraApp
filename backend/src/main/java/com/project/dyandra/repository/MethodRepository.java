package com.project.dyandra.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.dyandra.data.EMethod;
import com.project.dyandra.model.Methods;

@Repository
public interface MethodRepository extends JpaRepository<Methods, UUID> {
    Optional<Methods> findByName(EMethod name);
    
}
