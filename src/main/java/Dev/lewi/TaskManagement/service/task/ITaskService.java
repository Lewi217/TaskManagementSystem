package Dev.lewi.TaskManagement.service.task;

import Dev.lewi.TaskManagement.dto.TaskCreateDto;
import Dev.lewi.TaskManagement.dto.TaskResponseDto;
import Dev.lewi.TaskManagement.dto.TaskUpdateDto;
import Dev.lewi.TaskManagement.utils.enums.TaskPriority;
import Dev.lewi.TaskManagement.utils.enums.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface ITaskService {
    List<TaskResponseDto> getAllTasks();
    Optional<TaskResponseDto> getTaskById(Long id);
    TaskResponseDto createTask(TaskCreateDto taskCreateDto);
    TaskResponseDto updateTask(Long id, TaskUpdateDto taskUpdateDto);
    void deleteTask(Long id);
    List<TaskResponseDto> getTasksByStatus(TaskStatus status);
    List<TaskResponseDto> getTasksByPriority(TaskPriority priority);
    List<TaskResponseDto> searchTasks(String keyword);
}
