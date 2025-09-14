package com.justin.projectmanager.repository;

import com.justin.projectmanager.dto.TaskItem;
import com.justin.projectmanager.entity.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, UUID> {
    List<TaskItem> findAllBy();
    List<TaskItem> findAllByProjectStatusId(UUID projectStatusId);
}
