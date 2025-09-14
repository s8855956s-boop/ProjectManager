package com.justin.projectmanager.repository;

import com.justin.projectmanager.dto.BoardItem;
import com.justin.projectmanager.entity.ProjectBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectBoardRepository extends JpaRepository<ProjectBoard, UUID> {
    List<BoardItem> findAllBoardItemBy();
}
