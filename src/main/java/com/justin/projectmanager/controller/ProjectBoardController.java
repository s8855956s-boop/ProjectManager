package com.justin.projectmanager.controller;

import com.justin.projectmanager.dto.request.BoardRequest;
import com.justin.projectmanager.dto.response.BoardResponse;
import com.justin.projectmanager.service.ProjectBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projectBoards")
@Tag(name = "專案看板", description = "處理專案看板相關的 API")
public class ProjectBoardController {
    @Autowired
    private ProjectBoardService service;

    @PostMapping
    @Operation(summary = "新增專案看板", description = "根據 request 新增一個專案看板")
    public void createProjectBoard(@RequestBody BoardRequest request){
        service.createProjectBoard(request);
    }

    @GetMapping
    @Operation(summary = "取得所有專案看板", description = "取得所有專案看板")
    public List<BoardResponse> getAllBoardList(){
        return service.getAllBoardList();
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "取得專案看板", description = "根據 uuid 取得一個專案看板")
    public BoardResponse getBoardByUuid(@PathVariable UUID uuid){
        return service.getBoardByUuid(uuid);
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "更新專案看板", description = "根據 request 將 id為uuid 的專案看板更新")
    public void updateBoard(@PathVariable UUID uuid, @RequestBody BoardRequest request){
        service.updateBoard(request, uuid);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "刪除專案看板", description = "根據 uuid 刪除一個專案看板")
    public void deleteBoard(@PathVariable UUID uuid){
        service.deleteBoard(uuid);
    }
}
