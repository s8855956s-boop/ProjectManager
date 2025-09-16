package com.justin.projectmanager.service;

import com.justin.projectmanager.dto.UserItem;
import com.justin.projectmanager.dto.request.UserRequest;
import com.justin.projectmanager.dto.response.UserResponse;
import com.justin.projectmanager.entity.User;
import com.justin.projectmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    void createUser() {
        UserRequest request = new UserRequest();
        request.setUsername("Test User");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        service.createUser(request);

        verify(repository).save(captor.capture());

        assertThat(captor.getValue().getUsername()).isEqualTo("Test User");
    }

    @Test
    void getAllUsers() {
        UserItem userItem = mock(UserItem.class);
        when(userItem.getId()).thenReturn(UUID.randomUUID());
        when(userItem.getUsername()).thenReturn("Test User");

        when(repository.findAllBy()).thenReturn(List.of(userItem));

        List<UserResponse> responses = service.getAllUsers();
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getUsername()).isEqualTo("Test User");
    }

    @Test
    void getUserByUuid() {
        UserItem userItem = mock(UserItem.class);
        UUID userId = UUID.randomUUID();
        when(userItem.getId()).thenReturn(userId);
        when(userItem.getUsername()).thenReturn("Test User");

        when(repository.getUserItemById(userId)).thenReturn(Optional.of(userItem));

        UserResponse response = service.getUserByUuid(userId);

        assertThat(response.getUsername()).isEqualTo("Test User");
    }

    @Test
    void updateUserByUuid() {
        User existing = new User();
        UUID userId = UUID.randomUUID();
        existing.setId(userId);
        existing.setUsername("Old Username");

        UserRequest request = new UserRequest();
        request.setUsername("New Username");

        when(repository.findById(userId)).thenReturn(Optional.of(existing));

        service.updateUserByUuid(request, userId);

        assertThat(existing.getUsername()).isEqualTo("New Username");
    }

    @Test
    void updateUserByUuid_whenUserNotFound() {
        UUID userId = UUID.randomUUID();

        UserRequest request = new UserRequest();
        request.setUsername("New Username");

        when(repository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> service.updateUserByUuid(request, userId));
    }

    @Test
    void deleteUser() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);

        service.deleteUser(id);

        verify(repository).deleteById(id);
    }

    @Test
    void deleteUser_whenNotExists() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(false);

        verify(repository, never()).deleteById(id);

        assertThrows(NoSuchElementException.class, () -> service.deleteUser(id));
    }
}