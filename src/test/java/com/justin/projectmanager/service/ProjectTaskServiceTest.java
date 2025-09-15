package com.justin.projectmanager.service;

import com.justin.projectmanager.dto.TaskItem;
import com.justin.projectmanager.dto.request.TaskRequest;
import com.justin.projectmanager.dto.response.TaskResponse;
import com.justin.projectmanager.entity.ProjectTask;
import com.justin.projectmanager.repository.ProjectTaskRepository;
import com.justin.projectmanager.service.ProjectTaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectTaskServiceTest {

    @Mock
    private ProjectTaskRepository repository;

    @InjectMocks
    private ProjectTaskService service;

    @Test
    void testCreateProjectTask() {
        TaskRequest request = new TaskRequest();
        request.setName("Test Task");

        service.createProjectTask(request);

        // 確認 save 被呼叫
        ArgumentCaptor<ProjectTask> captor = ArgumentCaptor.forClass(ProjectTask.class);
        verify(repository).save(captor.capture());

        ProjectTask saved = captor.getValue();
        assertThat(saved.getName()).isEqualTo("Test Task");
        assertThat(saved.getCreateDate()).isNotNull();
    }

    @Test
    void testGetAllTasks() {
        TaskItem taskItem = mock(TaskItem.class);
        when(taskItem.getId()).thenReturn(UUID.randomUUID());
        when(taskItem.getName()).thenReturn("Test Task");
        when(repository.findAllBy()).thenReturn(List.of(taskItem));

        List<TaskResponse> responses = service.getAllTasks();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getName()).isEqualTo("Test Task");
    }

    @Test
    void testUpdateProjectTask() {
        UUID id = UUID.randomUUID();
        ProjectTask existing = new ProjectTask();
        existing.setId(id);
        existing.setName("Old Name");

        when(repository.findById(id)).thenReturn(Optional.of(existing));

        TaskRequest request = new TaskRequest();
        request.setName("New Name");

        service.updateProjectTask(request, id);

        verify(repository).save(existing);
        assertThat(existing.getName()).isEqualTo("New Name");
    }

    @Test
    void testUpdateProjectTask_NotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        TaskRequest request = new TaskRequest();
        request.setName("New Name");

        assertThrows(Exception.class, () -> service.updateProjectTask(request, id));
    }

    @Test
    void testDeleteStatus_WhenExists() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);

        service.deleteStatus(id);

        verify(repository).deleteById(id);
    }

    @Test
    void testDeleteStatus_WhenNotExists() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(false);

        verify(repository, never()).deleteById(id);

        assertThrows(NoSuchElementException.class, () -> service.deleteStatus(id));
    }
}
