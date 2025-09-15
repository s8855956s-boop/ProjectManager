package com.justin.projectmanager.service;

import com.justin.projectmanager.dto.BoardItem;
import com.justin.projectmanager.dto.request.BoardRequest;
import com.justin.projectmanager.dto.request.TaskRequest;
import com.justin.projectmanager.dto.response.BoardResponse;
import com.justin.projectmanager.entity.ProjectBoard;
import com.justin.projectmanager.repository.ProjectBoardRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectBoardServiceTest {
    @Mock
    ProjectBoardRepository repository;

    @InjectMocks
    ProjectBoardService service;

    @Test
    void createProjectBoard() {
        BoardRequest request = new BoardRequest();
        request.setName("Test Board");

        service.createProjectBoard(request);

        ArgumentCaptor<ProjectBoard> captor = ArgumentCaptor.forClass(ProjectBoard.class);
        verify(repository).save(captor.capture());



        ProjectBoard saved = captor.getValue();
        assertThat(saved.getName()).isEqualTo("Test Board");
        assertThat(saved.getCreateDate()).isNotNull();
    }

    @Test
    void getAllBoardList() {
        BoardItem boardItem = mock(BoardItem.class);
        when(boardItem.getId()).thenReturn(UUID.randomUUID());
        when(boardItem.getName()).thenReturn("Test Board");
        when(repository.findAllBoardItemBy()).thenReturn(List.of(boardItem));

        List<BoardResponse> responses = service.getAllBoardList();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getName()).isEqualTo("Test Board");
    }

    @Test
    void getBoardByUuid() {
        UUID uuid = UUID.randomUUID();

        BoardItem boardItem = mock(BoardItem.class);
        when(boardItem.getId()).thenReturn(UUID.randomUUID());
        when(boardItem.getName()).thenReturn("Test Board");
        when(repository.findBoardItemById(uuid)).thenReturn(Optional.of(boardItem));

        BoardResponse responses = service.getBoardByUuid(uuid);

        assertThat(responses).isNotNull();
        assertThat(responses.getName()).isEqualTo("Test Board");
    }

    @Test
    void updateBoard() {
        UUID id = UUID.randomUUID();
        ProjectBoard existing = new ProjectBoard();
        existing.setId(id);
        existing.setName("Old Name");

        BoardRequest request = new BoardRequest();
        request.setName("New Name");

        when(repository.findById(id)).thenReturn(Optional.of(existing));

        service.updateBoard(request, id);
        verify(repository).save(existing);
        assertThat(existing.getName()).isEqualTo("New Name");
    }

    @Test
    void updateProjectBoard_NotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        BoardRequest request = new BoardRequest();
        request.setName("New Name");

        assertThrows(Exception.class, () -> service.updateBoard(request, id));
    }

    @Test
    void deleteBoard() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);

        service.deleteBoard(id);

        verify(repository).deleteById(id);
    }

    @Test
    void deleteBoard_WhenNotExists() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(false);

        verify(repository, never()).deleteById(id);

        assertThrows(NoSuchElementException.class, () -> service.deleteBoard(id));
    }
}