package Dev.lewi.TaskManagement.service.task;

import Dev.lewi.TaskManagement.dto.TaskCreateDto;
import Dev.lewi.TaskManagement.dto.TaskResponseDto;
import Dev.lewi.TaskManagement.dto.TaskUpdateDto;
import Dev.lewi.TaskManagement.model.Task;
import Dev.lewi.TaskManagement.repository.TaskRepository;
import Dev.lewi.TaskManagement.utils.enums.TaskPriority;
import Dev.lewi.TaskManagement.utils.enums.TaskStatus;
import Dev.lewi.TaskManagement.utils.exceptions.AppUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findByOrderByCreatedAtDesc()
                .stream()
                .map(task -> modelMapper.map(task, TaskResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TaskResponseDto> getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(task -> modelMapper.map(task, TaskResponseDto.class));
    }

    @Override
    public TaskResponseDto createTask(TaskCreateDto taskCreateDto) {
        Task task = modelMapper.map(taskCreateDto, Task.class);
        task.setStatus(TaskStatus.PENDING);

        Task savedTask = taskRepository.save(task);
        return modelMapper.map(savedTask, TaskResponseDto.class);
    }

    @Override
    public TaskResponseDto updateTask(Long id, TaskUpdateDto taskUpdateDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        AppUtils.updateField(task::setTitle, taskUpdateDto.getTitle(), task.getTitle());
        AppUtils.updateField(task::setDescription, taskUpdateDto.getDescription(), task.getDescription());
        AppUtils.updateField(task::setStatus, taskUpdateDto.getStatus(), task.getStatus());
        AppUtils.updateField(task::setPriority, taskUpdateDto.getPriority(), task.getPriority());

        Task updatedTask = taskRepository.save(task);
        return modelMapper.map(updatedTask, TaskResponseDto.class);
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskResponseDto> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status)
                .stream()
                .map(task -> modelMapper.map(task, TaskResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDto> getTasksByPriority(TaskPriority priority) {
        return taskRepository.findByPriority(priority)
                .stream()
                .map(task -> modelMapper.map(task, TaskResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDto> searchTasks(String keyword) {
        return taskRepository.findByTitleContainingOrDescriptionContaining(keyword, keyword)
                .stream()
                .map(task -> modelMapper.map(task, TaskResponseDto.class))
                .collect(Collectors.toList());
    }
}
