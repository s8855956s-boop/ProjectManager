package com.justin.projectmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Id;

import java.util.List;
import java.util.UUID;

/**
 * 專案看板
 */
@Entity
@Getter
@Setter
public class Board extends BaseEntity {
    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Status> statuses;
}
