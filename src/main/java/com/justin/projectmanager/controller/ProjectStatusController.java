package com.justin.projectmanager.controller;

import com.justin.projectmanager.dto.request.StatusRequest;
import com.justin.projectmanager.dto.response.StatusResponse;
import com.justin.projectmanager.service.ProjectStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "專案狀態", description = "處理專案狀態相關的 API")
public class ProjectStatusController {
    @Autowired
    private ProjectStatusService service;

    @PostMapping("/projectBoards/{projectBoardId}/projectStatuses")
    @Operation(summary = "新增專案狀態", description = "根據 request 新增一個專案狀態 並關連到 id為projectBoardId的專案看板")
    public void createProjectStatus(@RequestBody StatusRequest request, @PathVariable UUID projectBoardId) {
        service.createProjectStatus(request, projectBoardId);
    }

    @GetMapping("/projectBoards/{projectBoardId}/projectStatuses")
    @Operation(summary = "取得專案狀態", description = "根據 projectBoardId 取得一個專案看板底下所有的專案狀態")
    public List<StatusResponse> getStatusesByBoardId(@PathVariable UUID projectBoardId) {
        return service.getStatusesByBoardId(projectBoardId);
    }

    @PutMapping("projectStatuses/{uuid}")
    @Operation(summary = "更新專案狀態", description = "根據 request 將 id為uuid 的專案狀態更新")
    public void updateStatus(@RequestBody StatusRequest request, @PathVariable UUID uuid) {
        service.updateStatus(request, uuid);
    }

    @DeleteMapping("/projectBoards/{projectBoardId}/projectStatuses/{uuid}")
    @Operation(summary = "刪除專案狀態", description = "將id=projectBoardId的projectBoard底下id=uuid的projectStatus從projectBoard的projectStatuses中移除，有設定OrphanRemoval=true，JPA會自動幫我刪除")
    public void deleteStatus(@PathVariable UUID uuid, @PathVariable UUID projectBoardId) {
        service.deleteStatus(uuid, projectBoardId);
    }
}
