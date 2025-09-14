package com.justin.projectmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Id;

import java.util.List;
import java.util.UUID;

/**
 * 專案任務狀態
 */
@Entity
@Getter
@Setter
public class ProjectStatus extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "projectStatus")
    private List<ProjectTask> statuses;

    @ManyToOne
    @JoinColumn(name = "project_board_id")
    private ProjectBoard projectBoard;
}
