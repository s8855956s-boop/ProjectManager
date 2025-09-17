package com.justin.projectmanager.repository;

import com.justin.projectmanager.dto.UserItem;
import com.justin.projectmanager.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<AppUser, UUID> {
    List<UserItem> findAllBy();

    Optional<UserItem> getUserItemById(@Param("id") UUID id);
}
