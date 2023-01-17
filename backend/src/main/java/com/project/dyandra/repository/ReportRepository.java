package com.project.dyandra.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.dyandra.model.Reports;

@Repository
public interface ReportRepository extends JpaRepository<Reports,UUID>{
    Optional<Reports> findByReportId(String reportId);
}
