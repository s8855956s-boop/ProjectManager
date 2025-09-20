package com.justin.projectmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 使用者
 */
@Entity
@Table(name = "APP_USER")
@Getter
@Setter
public class AppUser extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String username;

    @ManyToMany
    @JoinTable(
            name = "user_task", // 中介表名稱
            joinColumns = @JoinColumn(name = "user_id"),  // 對應到 AppUser
            inverseJoinColumns = @JoinColumn(name = "task_id") // 對應到 ProjectTask
    )
    private Set<ProjectTask> projectTasks = new HashSet<>();
}
