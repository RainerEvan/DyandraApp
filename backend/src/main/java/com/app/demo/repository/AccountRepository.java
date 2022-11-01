package com.app.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.demo.model.Accounts;

@Repository
public interface AccountRepository extends JpaRepository<Accounts,UUID>{
    Optional<Accounts> findByUsername(String username);
}
