package com.app.demo.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "m_reports")
public class Reports {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name="connection_id")
    private Connections connection;

    @ManyToOne
    @JoinColumn(name="source_path_id")
    private SourcePaths sourcePath;

    @Lob
    private String query;

    private String title;
    @Lob
    private String reportConfig;

    @Column(unique = true)
    private String reportId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
