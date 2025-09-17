package com.justin.projectmanager.controller;

import com.justin.projectmanager.dto.request.UserRequest;
import com.justin.projectmanager.dto.response.TaskResponse;
import com.justin.projectmanager.dto.response.UserResponse;
import com.justin.projectmanager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "使用者", description = "處理使用者相關的 API")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping
    @Operation(summary = "新增使用者", description = "根據 request 新增一個使用者")
    public void createUser(@RequestBody UserRequest request){
        service.createUser(request);
    }

    @GetMapping
    @Operation(summary = "取得所有使用者", description = "取得所有使用者")
    public List<UserResponse> getAllUsers(){
        return service.getAllUsers();
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "取得特定使用者", description = "取得id=uuid的使用者")
    public UserResponse getUserByUuid(@PathVariable UUID uuid){
        return service.getUserByUuid(uuid);
    }

    @GetMapping("/{uuid}/tasks")
    @Operation(summary = "取得特定使用者底下的所有任務", description = "取得特定使用者底下的所有任務")
    public List<TaskResponse> getUserTasks(@PathVariable UUID uuid){
        return service.getUserTasks(uuid);
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "更新特定使用者", description = "根據 request 更新 id=uuid的使用者")
    public void updateUserByUuid(@RequestBody UserRequest request, @PathVariable UUID uuid){
        service.updateUserByUuid(request, uuid);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "刪除特定使用者", description = "根據 request 刪除 id=uuid的使用者")
    public void deleteUserByUuid(@PathVariable UUID uuid){
        service.deleteUser(uuid);
    }
}
