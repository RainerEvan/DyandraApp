package com.app.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.demo.model.Connections;
import com.app.demo.model.SourcePaths;

@Repository
public interface SourcePathRepository extends JpaRepository<SourcePaths, UUID>{
    List<SourcePaths> findAllByConnection(Connections connection);
}
