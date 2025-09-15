package com.justin.projectmanager.service;

import com.justin.projectmanager.dto.StatusItem;
import com.justin.projectmanager.dto.request.BoardRequest;
import com.justin.projectmanager.dto.request.StatusRequest;
import com.justin.projectmanager.dto.response.StatusResponse;
import com.justin.projectmanager.entity.ProjectBoard;
import com.justin.projectmanager.entity.ProjectStatus;
import com.justin.projectmanager.repository.ProjectBoardRepository;
import com.justin.projectmanager.repository.ProjectStatusRepository;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectStatusServiceTest {
    @Mock
    ProjectStatusRepository repository;

    @Mock
    ProjectBoardRepository projectBoardRepository;

    @InjectMocks
    ProjectStatusService service;

    @Test
    void createProjectStatus() {
        StatusRequest request = new StatusRequest();
        request.setName("Test Status");

        UUID projectBoardId   = UUID.fromString("11111111-1111-1111-1111-111111111111");

        ProjectBoard projectBoard = new ProjectBoard();
        projectBoard.setId(projectBoardId);
        projectBoard.setName("Test Board");
        when(projectBoardRepository.findById(projectBoardId)).thenReturn(Optional.of(projectBoard));

        service.createProjectStatus(request, projectBoardId);

        verify(projectBoardRepository).save(projectBoard);

        assertThat(projectBoard.getProjectStatuses().stream().filter(status -> status.getName().equals("Test Status")).findFirst()).isNotEmpty();
    }

    @Test
    void getStatusesByBoardId() {

        UUID projectStatusId   = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID projectBoardId = UUID.fromString("22222222-2222-2222-2222-222222222222");

        StatusItem statusItem = mock(StatusItem.class);
        when(statusItem.getId()).thenReturn(projectStatusId);
        when(statusItem.getName()).thenReturn("Test Status");
        when(repository.findByProjectBoardId(projectBoardId)).thenReturn(List.of(statusItem));

        List<StatusResponse> responses = service.getStatusesByBoardId(projectBoardId);

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getName()).isEqualTo("Test Status");
    }

    @Test
    void updateStatus() {
        StatusRequest request = new StatusRequest();
        request.setName("New Name");

        UUID projectStatusId   = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID projectBoardId = UUID.fromString("22222222-2222-2222-2222-222222222222");

        ProjectBoard projectBoard = new ProjectBoard();

        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setId(projectStatusId);
        projectStatus.setName("Old Name");

        projectBoard.setProjectStatuses(List.of(projectStatus));

        when(projectBoardRepository.findById(projectBoardId)).thenReturn(Optional.of(projectBoard));

        service.updateStatus(request, projectStatusId, projectBoardId);
        verify(projectBoardRepository).save(projectBoard);
        Optional<ProjectStatus> updatedProjectStatus = projectBoard.getProjectStatuses()
                .stream()
                .filter(status -> status.getId().equals(projectStatusId))
                .findFirst();
        String updatedName = updatedProjectStatus.map(ProjectStatus::getName)
                        .orElse("");
        assertThat(updatedName).isEqualTo("New Name");
    }

    @Test
    void updateStatus_WhenProjectBoardNotFound() {
        StatusRequest request = new StatusRequest();
        request.setName("New Name");

        UUID projectStatusId   = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID projectBoardId = UUID.fromString("22222222-2222-2222-2222-222222222222");

        ProjectBoard projectBoard = new ProjectBoard();

        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setId(projectStatusId);
        projectStatus.setName("Old Name");

        projectBoard.setProjectStatuses(List.of(projectStatus));

        when(projectBoardRepository.findById(projectBoardId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> service.updateStatus(request, projectStatusId, projectBoardId));
    }

    @Test
    void updateStatus_WhenNoMatchedStatus() {
        StatusRequest request = new StatusRequest();
        request.setName("New Name");

        UUID projectStatusId   = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID projectBoardId    = UUID.fromString("22222222-2222-2222-2222-222222222222");
        UUID differentStatusId = UUID.fromString("33333333-3333-3333-3333-333333333333");

        ProjectBoard projectBoard = new ProjectBoard();

        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setId(differentStatusId);
        projectStatus.setName("Old Name");

        projectBoard.setProjectStatuses(List.of(projectStatus));

        when(projectBoardRepository.findById(projectBoardId)).thenReturn(Optional.of(projectBoard));

        assertThrows(Exception.class, () -> service.updateStatus(request, projectStatusId, projectBoardId));
    }

    @Test
    void deleteStatus() {
        UUID projectStatusId   = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID projectBoardId = UUID.fromString("22222222-2222-2222-2222-222222222222");

        ProjectBoard projectBoard = new ProjectBoard();
        projectBoard.setId(projectBoardId);
        projectBoard.setName("Test Board");

        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setId(projectStatusId);
        projectStatus.setName("Test Status");

        projectBoard.addProjectStatus(projectStatus);

        when(projectBoardRepository.findById(projectBoardId)).thenReturn(Optional.of(projectBoard));

        service.deleteStatus(projectStatusId, projectBoardId);

        assertTrue(projectBoard.getProjectStatuses().isEmpty());
    }

    @Test
    void deleteStatus_WhenProjectBoardNotFound() {
        UUID projectStatusId   = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID projectBoardId = UUID.fromString("22222222-2222-2222-2222-222222222222");

        when(projectBoardRepository.findById(projectBoardId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> service.deleteStatus(projectStatusId, projectBoardId));
    }

    @Test
    void deleteStatus_WhenNoMatchedStatus() {
        UUID projectStatusId   = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID projectBoardId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        UUID differentStatusId = UUID.fromString("33333333-3333-3333-3333-333333333333");

        ProjectBoard projectBoard = new ProjectBoard();
        projectBoard.setId(projectBoardId);
        projectBoard.setName("Test Board");

        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setId(differentStatusId);
        projectStatus.setName("Test Status");

        projectBoard.setProjectStatuses(List.of(projectStatus));

        when(projectBoardRepository.findById(projectBoardId)).thenReturn(Optional.of(projectBoard));

        assertThrows(NoSuchElementException.class, () -> service.deleteStatus(projectStatusId, projectBoardId));
    }
}
