package com.justin.projectmanager.repository;

import com.justin.projectmanager.dto.StatusItem;
import com.justin.projectmanager.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, UUID> {
    List<StatusItem> findAllBy();
    List<StatusItem> getStatusItemByProjectBoardId(UUID projectBoardId);
    List<ProjectStatus> findByProjectBoardId(UUID projectBoardId);
}
