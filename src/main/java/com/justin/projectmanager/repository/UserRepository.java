package com.justin.projectmanager.repository;

import com.justin.projectmanager.dto.UserItem;
import com.justin.projectmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<UserItem> findAllBy();
    Optional<UserItem> findUserItemById(UUID uuid);
}
