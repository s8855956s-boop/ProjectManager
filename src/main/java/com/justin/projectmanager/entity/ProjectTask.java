package com.justin.projectmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 專案任務
 */
@Entity
@Table(name = "PROJECT_TASK")
@Getter
@Setter
public class ProjectTask extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "project_status_id")
    private ProjectStatus projectStatus;

    @ManyToMany(mappedBy = "projectTasks")
    private Set<AppUser> appUsers = new HashSet<>();
}
