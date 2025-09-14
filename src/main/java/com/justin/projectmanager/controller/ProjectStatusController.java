package com.justin.projectmanager.controller;

import com.justin.projectmanager.dto.request.StatusRequest;
import com.justin.projectmanager.dto.response.StatusResponse;
import com.justin.projectmanager.service.ProjectStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projectBoards/{projectBoardId}/projectStatuses")
public class ProjectStatusController {
    @Autowired
    private ProjectStatusService service;

    @PostMapping
    public void createProjectStatus(@RequestBody StatusRequest request, @PathVariable UUID projectBoardId) {
        service.createProjectStatus(request, projectBoardId);
    }

    @GetMapping
    public List<StatusResponse> getStatusesByBoardId(@PathVariable UUID projectBoardId) {
        return service.getStatusesByBoardId(projectBoardId);
    }

    @PutMapping("/{uuid}")
    public void updateStatus(@RequestBody StatusRequest request, @PathVariable UUID uuid, @PathVariable UUID projectBoardId) {
        service.updateStatus(request, uuid, projectBoardId);
    }

    @DeleteMapping("/{uuid}")
    public void deleteStatus(@PathVariable UUID uuid, @PathVariable UUID projectBoardId) {
        service.deleteStatus(uuid, projectBoardId);
    }
}
