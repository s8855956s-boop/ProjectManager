package com.justin.projectmanager.service;

import com.justin.projectmanager.dto.TaskItem;
import com.justin.projectmanager.dto.request.TaskRequest;
import com.justin.projectmanager.dto.response.TaskResponse;
import com.justin.projectmanager.entity.ProjectStatus;
import com.justin.projectmanager.entity.ProjectTask;
import com.justin.projectmanager.repository.ProjectTaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectTaskService {
    @Autowired
    private ProjectTaskRepository repository;

    public void createProjectTask(TaskRequest request) {
        ProjectTask projectTask = new ProjectTask();
        projectTask.setName(request.getName());

        setAuditFields(projectTask, true);
        repository.save(projectTask);
    }

    public List<TaskResponse> getAllTasks() {
        List<TaskItem> taskItemList = repository.findAllBy();

        return taskItemList.stream().map(item -> {
            TaskResponse taskResponse = new TaskResponse();
            BeanUtils.copyProperties(item, taskResponse);
            return taskResponse;
        }).toList();
    }

    public List<TaskResponse> getTasksByStatusId(UUID projectStatusId) {
        List<TaskItem> taskItemList = repository.findAllByProjectStatusId(projectStatusId);

        return taskItemList.stream().map(item -> {
            TaskResponse taskResponse = new TaskResponse();
            BeanUtils.copyProperties(item, taskResponse);
            return taskResponse;
        }).toList();
    }

    public void updateProjectTask(TaskRequest taskRequest, UUID uuid) {
        ProjectTask projectTask = repository.findById(uuid).orElseThrow();
        projectTask.setName(taskRequest.getName());

        setAuditFields(projectTask, false);
        repository.save(projectTask);
    }

    public void deleteStatus(UUID uuid) {
        if (repository.existsById(uuid)) {
            repository.deleteById(uuid);
        }
    }

    private void setAuditFields(ProjectTask projectTask, boolean isNew) {
        LocalDateTime now = LocalDateTime.now();
        if (isNew) {
            projectTask.setCreateDate(now);
            projectTask.setCreateUser("NotSet");
        }
        projectTask.setModifyDate(now);
        projectTask.setModifyUser("NotSet");
    }
}
