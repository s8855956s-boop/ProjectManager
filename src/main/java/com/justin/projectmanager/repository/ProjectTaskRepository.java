package com.justin.projectmanager.repository;

import com.justin.projectmanager.dto.TaskItem;
import com.justin.projectmanager.dto.response.TaskResponse;
import com.justin.projectmanager.entity.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, UUID> {
    List<TaskItem> findAllBy();
    List<TaskItem> findAllByProjectStatusId(UUID projectStatusId);
    List<TaskItem> findByAppUsers_Id(@Param("userId") UUID userId);

    @Query(value = """
            SELECT pt.id, pt.name, ps.id, ps.name FROM PROJECT_TASK pt LEFT JOIN PROJECT_STATUS ps ON pt.project_status_id = ps.id
            """, nativeQuery = true)
    List<Object[]> getAllBasicInfo();
}
