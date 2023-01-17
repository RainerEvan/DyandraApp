package com.project.dyandra.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.dyandra.model.Accounts;

@Repository
public interface AccountRepository extends JpaRepository<Accounts,UUID>{
    Optional<Accounts> findByUserId(String userId);
}
