package com.justin.projectmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 專案看板
 */
@Entity
@Getter
@Setter
public class ProjectBoard extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "projectBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectStatus> projectStatuses = new ArrayList<>();

    public void addProjectStatus(ProjectStatus status) {
        projectStatuses.add(status);
        status.setProjectBoard(this);
    }

    public void removeProjectStatus(UUID projectStatusId) {
        ProjectStatus projectStatus = projectStatuses.stream().filter(status -> projectStatusId.equals(status.getId())).findFirst().orElseThrow();
        projectStatuses.remove(projectStatus);
        projectStatus.setProjectBoard(null);
    }
}
