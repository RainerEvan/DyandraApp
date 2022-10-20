package com.app.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.demo.model.ReportTokens;

@Repository
public interface ReportTokenRepository extends JpaRepository<ReportTokens,UUID>{
    Optional<ReportTokens> findByToken(String token);
}
