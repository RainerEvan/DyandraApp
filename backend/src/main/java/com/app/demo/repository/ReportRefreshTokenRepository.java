package com.app.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.demo.model.ReportRefreshTokens;

@Repository
public interface ReportRefreshTokenRepository extends JpaRepository<ReportRefreshTokens,UUID>{
    Optional<ReportRefreshTokens> findByToken(String token);
}
