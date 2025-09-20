package com.justin.projectmanager.service;

import com.justin.projectmanager.dto.BoardItem;
import com.justin.projectmanager.dto.request.BoardRequest;
import com.justin.projectmanager.dto.response.BoardResponse;
import com.justin.projectmanager.entity.ProjectBoard;
import com.justin.projectmanager.entity.ProjectStatus;
import com.justin.projectmanager.entity.ProjectTask;
import com.justin.projectmanager.repository.ProjectBoardRepository;
import com.justin.projectmanager.repository.ProjectStatusRepository;
import com.justin.projectmanager.repository.ProjectTaskRepository;
import com.justin.projectmanager.utils.BaseEntityUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
public class ProjectBoardService {
    @Autowired
    private ProjectBoardRepository repository;

    @Autowired
    ProjectStatusRepository projectStatusRepository;

    @Autowired
    ProjectTaskRepository projectTaskRepository;

    public void createProjectBoard(BoardRequest request) {
        ProjectBoard projectBoard = new ProjectBoard();
        projectBoard.setName(request.getName());

        BaseEntityUtils.setAuditFields(projectBoard, true);
        repository.save(projectBoard);
    }

    public List<BoardResponse> getAllBoardList() {
        List<BoardItem> boardItemList = repository.findAllBoardItemBy();

        //將Projection轉換為Response類別
        return boardItemList.stream().map(item -> {
            BoardResponse response = new BoardResponse();
            BeanUtils.copyProperties(item, response);
            return response;
        }).toList();
    }

    public BoardResponse getBoardByUuid(UUID uuid) {
        BoardItem boardItem = repository.findBoardItemById(uuid).orElseThrow();
        BoardResponse boardResponse = new BoardResponse();
        BeanUtils.copyProperties(boardItem, boardResponse);
        return boardResponse;
    }

    public void updateBoard(BoardRequest request, UUID uuid) {
        ProjectBoard projectBoard = repository.findById(uuid).orElseThrow();
        projectBoard.setName(request.getName());

        BaseEntityUtils.setAuditFields(projectBoard, false);
        repository.save(projectBoard);
    }

    @Transactional
    public void deleteBoard(UUID uuid) {
        if (repository.existsById(uuid)) {
            List<ProjectStatus> statuses = projectStatusRepository.findByProjectBoardId(uuid);
            for (ProjectStatus status : statuses) {
                List<ProjectTask> tasks = status.getTasks();
                tasks.forEach(task -> task.setProjectStatus(null));
                status.getTasks().clear();
            }

            projectStatusRepository.saveAllAndFlush(statuses);

            repository.deleteById(uuid);
        } else {
            throw new NoSuchElementException();
        }
    }
}
