package com.justin.projectmanager.service;

import com.justin.projectmanager.dto.UserItem;
import com.justin.projectmanager.dto.request.UserRequest;
import com.justin.projectmanager.dto.response.UserResponse;
import com.justin.projectmanager.entity.User;
import com.justin.projectmanager.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void createUser(UserRequest request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);

        setAuditFields(user, true);
        repository.save(user);
    }

    public List<UserResponse> getAllUsers() {
        return repository.findAllBy().stream().map(item -> {
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(item, userResponse);
            return userResponse;
        }).toList();
    }

    public UserResponse getUserByUuid(UUID uuid) {
        UserItem userItem = repository.findUserItemById(uuid).orElseThrow();
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(userItem, userResponse);
        return userResponse;
    }

    public void updateUserByUuid(UserRequest request, UUID uuid) {
        User user = repository.findById(uuid).orElseThrow();
        BeanUtils.copyProperties(request, user);

        setAuditFields(user, false);
        repository.save(user);
    }

    public void deleteUser(UUID uuid) {
        if (repository.existsById(uuid)) {
            repository.deleteById(uuid);
        }
    }

    private void setAuditFields(User user, boolean isNew) {
        LocalDateTime now = LocalDateTime.now();
        if (isNew) {
            user.setCreateDate(now);
            user.setCreateUser("NotSet");
        }
        user.setModifyDate(now);
        user.setModifyUser("NotSet");
    }
}
