package com.justin.projectmanager.controller;

import com.justin.projectmanager.dto.request.TaskRequest;
import com.justin.projectmanager.dto.response.TaskResponse;
import com.justin.projectmanager.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProjectTaskController {
    @Autowired
    private ProjectTaskService service;

    @PostMapping("/projectTasks")
    public void createProjectTask(@RequestBody TaskRequest request) {
        service.createProjectTask(request);
    }

    @GetMapping("/projectTasks")
    public List<TaskResponse> getAllTasks() {
        return service.getAllTasks();
    }

    @GetMapping("projectStatuses/{projectStatusId}/projectTasks")
    public List<TaskResponse> getTasksByStatusId(@PathVariable UUID projectStatusId) {
        return service.getTasksByStatusId(projectStatusId);
    }

    @PutMapping("/projectTasks/{uuid}")
    public void updateProjectTask(@PathVariable UUID uuid, @RequestBody TaskRequest taskRequest) {
        service.updateProjectTask(taskRequest, uuid);
    }

    @DeleteMapping("projectTasks/{uuid}")
    public void deleteStatus(@PathVariable UUID uuid) {
        service.deleteStatus(uuid);
    }
}
