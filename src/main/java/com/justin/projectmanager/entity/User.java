package com.justin.projectmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

/**
 * 使用者
 */
@Entity
@Getter
@Setter
public class User extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String username;

    @ManyToMany
    @JoinTable(
            name = "user_task", // 中介表名稱
            joinColumns = @JoinColumn(name = "user_id"),  // 對應到 User
            inverseJoinColumns = @JoinColumn(name = "task_id") // 對應到 ProjectTask
    )
    private Set<ProjectTask> projectTasks;
}
