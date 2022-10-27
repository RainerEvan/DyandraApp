package com.app.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.demo.model.Applications;

@Repository
public interface ApplicationRepository extends JpaRepository<Applications, UUID>{
    Optional<Applications> findByClientId(String clientId);
    boolean existsByName(String name);
    boolean existsByCode(String code);
}
