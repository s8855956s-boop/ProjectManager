package com.justin.projectmanager.controller;

import com.justin.projectmanager.dto.request.BoardRequest;
import com.justin.projectmanager.dto.response.BoardResponse;
import com.justin.projectmanager.service.ProjectBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projectBoards")
public class ProjectBoardController {
    @Autowired
    private ProjectBoardService service;

    @PostMapping
    public void createProjectBoard(@RequestBody BoardRequest request){
        service.createProjectBoard(request);
    }

    @GetMapping
    public List<BoardResponse> getAllBoardList(){
        return service.getAllBoardList();
    }

    @GetMapping("/{uuid}")
    public BoardResponse getBoardByUuid(@PathVariable UUID uuid){
        return service.getBoardByUuid(uuid);
    }

    @PutMapping("/{uuid}")
    public void updateBoard(@PathVariable UUID uuid, @RequestBody BoardRequest request){
        service.updateBoard(request, uuid);
    }

    @DeleteMapping("/{uuid}")
    public void deleteBoard(@PathVariable UUID uuid){
        service.deleteBoard(uuid);
    }
}
