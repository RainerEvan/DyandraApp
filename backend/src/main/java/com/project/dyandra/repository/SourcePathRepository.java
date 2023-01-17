package com.project.dyandra.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.dyandra.model.Connections;
import com.project.dyandra.model.SourcePaths;

@Repository
public interface SourcePathRepository extends JpaRepository<SourcePaths, UUID>{
    List<SourcePaths> findAllByConnection(Connections connection);
}
