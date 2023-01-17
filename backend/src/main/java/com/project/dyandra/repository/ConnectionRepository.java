package com.project.dyandra.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.dyandra.model.Connections;

@Repository
public interface ConnectionRepository extends JpaRepository<Connections, UUID>{
    
}
