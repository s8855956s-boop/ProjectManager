package com.justin.projectmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

/**
 * 專案任務
 */
@Entity
@Getter
@Setter
public class Task extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToMany(mappedBy = "tasks")
    private Set<User> users;
}
