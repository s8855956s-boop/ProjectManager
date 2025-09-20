package com.justin.projectmanager.service;

import com.justin.projectmanager.dto.TaskItem;
import com.justin.projectmanager.dto.request.TaskRequest;
import com.justin.projectmanager.dto.response.TaskResponse;
import com.justin.projectmanager.entity.AppUser;
import com.justin.projectmanager.entity.ProjectStatus;
import com.justin.projectmanager.entity.ProjectTask;
import com.justin.projectmanager.repository.ProjectStatusRepository;
import com.justin.projectmanager.repository.ProjectTaskRepository;
import com.justin.projectmanager.repository.UserRepository;
import com.justin.projectmanager.utils.BaseEntityUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProjectTaskService {
    @Autowired
    private ProjectTaskRepository repository;
    
    @Autowired
    private ProjectStatusRepository projectStatusRepository;

    @Autowired
    private UserRepository userRepository;

    public void createProjectTask(TaskRequest request) {
        ProjectTask projectTask = new ProjectTask();
        projectTask.setName(request.getName());

        BaseEntityUtils.setAuditFields(projectTask, true);
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

        BaseEntityUtils.setAuditFields(projectTask, false);
        repository.save(projectTask);
    }

    @Transactional
    public void changeStatus(UUID uuid, UUID projectStatusId) {
        List<ProjectStatus> statuses = new ArrayList<>();
        ProjectTask projectTask = repository.findById(uuid).orElseThrow();
        ProjectStatus oldProjectStatus = projectTask.getProjectStatus();
        //將任務從舊狀態中移除
        oldProjectStatus.getTasks().remove(projectTask);
        ProjectStatus newProjectStatus = projectStatusRepository.findById(projectStatusId).orElseThrow();
        //將新狀態加入任務中
        projectTask.setProjectStatus(newProjectStatus);
        //將任務加入新狀態
        newProjectStatus.getTasks().add(projectTask);

        statuses.add(oldProjectStatus);
        statuses.add(newProjectStatus);

        for(ProjectStatus status : statuses){
            BaseEntityUtils.setAuditFields(status, false);
        }

        BaseEntityUtils.setAuditFields(projectTask, false);
        projectStatusRepository.saveAll(statuses);
        repository.save(projectTask);
    }

    @Transactional
    public void assignTask(UUID uuid, UUID appUserId) {
        ProjectTask projectTask = repository.findById(uuid).orElseThrow();
        AppUser user = userRepository.findById(appUserId).orElseThrow();

        projectTask.getAppUsers().add(user);
        user.getProjectTasks().add(projectTask);

        repository.save(projectTask);
        userRepository.save(user);
    }

    public void deleteStatus(UUID uuid) {
        if (repository.existsById(uuid)) {
            repository.deleteById(uuid);
        } else {
            throw new NoSuchElementException();
        }
    }
}
