package com.justin.projectmanager.service;

import com.justin.projectmanager.dto.request.StatusRequest;
import com.justin.projectmanager.dto.StatusItem;
import com.justin.projectmanager.dto.response.StatusResponse;
import com.justin.projectmanager.entity.ProjectBoard;
import com.justin.projectmanager.entity.ProjectStatus;
import com.justin.projectmanager.repository.ProjectBoardRepository;
import com.justin.projectmanager.repository.ProjectStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProjectStatusService {
    @Autowired
    private ProjectStatusRepository repository;

    @Autowired
    private ProjectBoardRepository projectBoardRepository;

    @Transactional
    public void createProjectStatus(StatusRequest request, UUID projectBoardId) {
        ProjectBoard projectBoard = projectBoardRepository.findById(projectBoardId).orElseThrow();

        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setName(request.getName());

        setAuditFields(projectStatus, true);

        projectBoard.addProjectStatus(projectStatus);

        projectBoardRepository.save(projectBoard);// 因為 cascade = ALL，projectStatus 會跟著存進去
    }

    public List<StatusResponse> getStatusesByBoardId(UUID projectBoardId) {
        List<StatusItem> statusItemList = repository.getStatusItemByProjectBoardId(projectBoardId);

        //將Projection轉換為Response類別
        return statusItemList.stream().map(item -> {
            StatusResponse response = new StatusResponse();
            BeanUtils.copyProperties(item, response);
            return response;
        }).toList();
    }

    public void updateStatus(StatusRequest request, UUID uuid) {
        ProjectStatus projectStatus = repository.findById(uuid).orElseThrow();
        projectStatus.setName(request.getName());
        setAuditFields(projectStatus, false);

        repository.save(projectStatus);
    }

    @Transactional
    public void deleteStatus(UUID uuid, UUID projectBoardId) {
        ProjectBoard projectBoard = projectBoardRepository.findById(projectBoardId).orElseThrow();
        if (projectBoard.getProjectStatuses().stream().anyMatch(status -> uuid.equals(status.getId()))) {
            projectBoard.removeProjectStatus(uuid);
        } else {
            throw new NoSuchElementException();
        }
    }

    private void setAuditFields(ProjectStatus projectStatus, boolean isNew) {
        LocalDateTime now = LocalDateTime.now();
        if (isNew) {
            projectStatus.setCreateDate(now);
            projectStatus.setCreateUser("NotSet");
        }
        projectStatus.setModifyDate(now);
        projectStatus.setModifyUser("NotSet");
    }
}
