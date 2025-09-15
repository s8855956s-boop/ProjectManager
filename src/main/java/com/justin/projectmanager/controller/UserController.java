package com.justin.projectmanager.controller;

import com.justin.projectmanager.dto.request.UserRequest;
import com.justin.projectmanager.dto.response.UserResponse;
import com.justin.projectmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping
    public void createUser(@RequestBody UserRequest request){
        service.createUser(request);
    }

    @GetMapping
    public List<UserResponse> getAllUsers(){
        return service.getAllUsers();
    }

//    @GetMapping("/{uuid}")
//    public UserResponse getUserByUuid(@PathVariable UUID uuid){
//        return service.getUserByUuid(uuid);
//    }

    @PutMapping("/{uuid}")
    public void updateUserByUuid(@RequestBody UserRequest request, @PathVariable UUID uuid){
        service.updateUserByUuid(request, uuid);
    }

    @DeleteMapping("/{uuid}")
    public void updateUserByUuid(@PathVariable UUID uuid){
        service.deleteUser(uuid);
    }
}
