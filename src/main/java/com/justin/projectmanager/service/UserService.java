package com.justin.projectmanager.service;

import com.justin.projectmanager.dto.UserItem;
import com.justin.projectmanager.dto.request.UserRequest;
import com.justin.projectmanager.dto.response.TaskResponse;
import com.justin.projectmanager.dto.response.UserResponse;
import com.justin.projectmanager.entity.AppUser;
import com.justin.projectmanager.repository.ProjectTaskRepository;
import com.justin.projectmanager.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public void createUser(UserRequest request) {
        AppUser appUser = new AppUser();
        BeanUtils.copyProperties(request, appUser);

        setAuditFields(appUser, true);
        repository.save(appUser);
    }

    public List<UserResponse> getAllUsers() {
        return repository.findAllBy().stream().map(item -> {
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(item, userResponse);
            return userResponse;
        }).toList();
    }

    public UserResponse getUserByUuid(UUID uuid) {
        UserItem userItem = repository.getUserItemById(uuid).orElseThrow();
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(userItem, userResponse);
        return userResponse;
    }

    public void updateUserByUuid(UserRequest request, UUID uuid) {
        AppUser appUser = repository.findById(uuid).orElseThrow();
        BeanUtils.copyProperties(request, appUser);

        setAuditFields(appUser, false);
        repository.save(appUser);
    }

    public void deleteUser(UUID uuid) {
        if (repository.existsById(uuid)) {
            repository.deleteById(uuid);
        } else {
            throw new NoSuchElementException();
        }
    }

    public List<TaskResponse> getUserTasks(UUID uuid) {
        return projectTaskRepository.findByAppUsers_Id(uuid).stream()
                .map(item -> {
                    TaskResponse taskResponse = new TaskResponse();
                    BeanUtils.copyProperties(item, taskResponse);
                    return taskResponse;
                }).toList();
    }

    private void setAuditFields(AppUser appUser, boolean isNew) {
        LocalDateTime now = LocalDateTime.now();
        if (isNew) {
            appUser.setCreateDate(now);
            appUser.setCreateUser("NotSet");
        }
        appUser.setModifyDate(now);
        appUser.setModifyUser("NotSet");
    }
}
