package com.justin.projectmanager.controller;

import com.justin.projectmanager.dto.request.TaskRequest;
import com.justin.projectmanager.dto.response.TaskResponse;
import com.justin.projectmanager.service.ProjectTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "專案任務", description = "處理專案任務相關的 API")
public class ProjectTaskController {
    @Autowired
    private ProjectTaskService service;

    @PostMapping("/projectTasks")
    @Operation(summary = "新增專案任務", description = "根據 request 新增一個專案任務")
    public void createProjectTask(@RequestBody TaskRequest request) {
        service.createProjectTask(request);
    }

    @GetMapping("/projectTasks")
    @Operation(summary = "取得所有專案任務", description = "取得所有專案任務")
    public List<TaskResponse> getAllTasks() {
        return service.getAllTasks();
    }

    @GetMapping("/projectStatuses/{projectStatusId}/projectTasks")
    @Operation(summary = "取得專案狀態id=projectStatusId底下的所有專案任務", description = "取得專案狀態id=projectStatusId底下的所有專案任務")
    public List<TaskResponse> getTasksByStatusId(@PathVariable UUID projectStatusId) {
        return service.getTasksByStatusId(projectStatusId);
    }

    @PutMapping("/projectTasks/{uuid}")
    @Operation(summary = "更新專案任務", description = "根據 request 更新id=uuid的專案任務")
    public void updateProjectTask(@PathVariable UUID uuid, @RequestBody TaskRequest request) {
        service.updateProjectTask(request, uuid);
    }

    @DeleteMapping("/projectTasks/{uuid}")
    @Operation(summary = "刪除專案任務", description = "根據 uuid 刪除專案任務")
    public void deleteStatus(@PathVariable UUID uuid) {
        service.deleteStatus(uuid);
    }

    @PutMapping("/projectTasks/{uuid}/projectStatuses/{projectStatusId}")
    @Operation(summary = "修改狀態", description = "將uuid的projectTask 的狀態改為 id為projectStatusId的projectStatus")
    public void changeStatus(@PathVariable UUID uuid, @PathVariable UUID projectStatusId) {
        service.changeStatus(uuid, projectStatusId);
    }

    @PutMapping("/projectTasks/{uuid}/appUsers/{appUserId}")
    @Operation(summary = "指派專案任務", description = "指派專案任務給特定使用者")
    public void assignTask(@PathVariable UUID uuid, @PathVariable UUID appUserId) {
        service.assignTask(uuid, appUserId);
    }
}
