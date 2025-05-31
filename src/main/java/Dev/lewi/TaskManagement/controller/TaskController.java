package Dev.lewi.TaskManagement.controller;

import Dev.lewi.TaskManagement.dto.TaskCreateDto;
import Dev.lewi.TaskManagement.dto.TaskResponseDto;
import Dev.lewi.TaskManagement.dto.TaskUpdateDto;
import Dev.lewi.TaskManagement.response.ApiResponse;
import Dev.lewi.TaskManagement.service.task.ITaskService;
import Dev.lewi.TaskManagement.utils.enums.TaskPriority;
import Dev.lewi.TaskManagement.utils.enums.TaskStatus;
import Dev.lewi.TaskManagement.utils.exceptions.CustomExceptionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static Dev.lewi.TaskManagement.utils.exceptions.ApiResponseUtils.REQUEST_ERROR_MESSAGE;
import static Dev.lewi.TaskManagement.utils.exceptions.ApiResponseUtils.REQUEST_SUCCESS_MESSAGE;
import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/tasks")
public class TaskController {

    private final ITaskService ItaskService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) String search) {
        try {
            List<TaskResponseDto> tasks;

            if (search != null && !search.trim().isEmpty()) {
                tasks = ItaskService.searchTasks(search);
            } else if (status != null) {
                tasks = ItaskService.getTasksByStatus(status);
            } else if (priority != null) {
                tasks = ItaskService.getTasksByPriority(priority);
            } else {
                tasks = ItaskService.getAllTasks();
            }

            return ResponseEntity.ok(new ApiResponse(REQUEST_SUCCESS_MESSAGE, tasks));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(REQUEST_ERROR_MESSAGE, e.getMessage()));
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getTaskById(@PathVariable Long id) {
        try {
            TaskResponseDto task = ItaskService.getTaskById(id)
                    .orElseThrow(() -> new CustomExceptionResponse("Task not found"));
            return ResponseEntity.ok(new ApiResponse(REQUEST_SUCCESS_MESSAGE, task));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(REQUEST_ERROR_MESSAGE, "Error fetching Task: " + e.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createTask(@Valid @RequestBody TaskCreateDto taskCreateDto) {
        try {
            TaskResponseDto createdTask = ItaskService.createTask(taskCreateDto);
            return ResponseEntity.status(CREATED)
                    .body(new ApiResponse(REQUEST_SUCCESS_MESSAGE, createdTask));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(REQUEST_ERROR_MESSAGE, e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateDto taskUpdateDto) {
        try {
            TaskResponseDto updatedTask = ItaskService.updateTask(id, taskUpdateDto);
            return ResponseEntity.ok(new ApiResponse(REQUEST_SUCCESS_MESSAGE, updatedTask));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(REQUEST_ERROR_MESSAGE, e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable Long id) {
        try {
            ItaskService.deleteTask(id);
            return ResponseEntity.ok(new ApiResponse(REQUEST_SUCCESS_MESSAGE, "Task has been deleted successfully"));
        } catch (CustomExceptionResponse e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(REQUEST_ERROR_MESSAGE, e.getMessage()));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse> getTasksByStatus(@PathVariable TaskStatus status) {
        try {
            List<TaskResponseDto> tasks = ItaskService.getTasksByStatus(status);
            return ResponseEntity.ok(new ApiResponse(REQUEST_SUCCESS_MESSAGE, tasks));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(REQUEST_ERROR_MESSAGE, e.getMessage()));
        }
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<ApiResponse> getTasksByPriority(@PathVariable TaskPriority priority) {
        try {
            List<TaskResponseDto> tasks = ItaskService.getTasksByPriority(priority);
            return ResponseEntity.ok(new ApiResponse(REQUEST_SUCCESS_MESSAGE, tasks));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(REQUEST_ERROR_MESSAGE, e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchTasks(@RequestParam String keyword) {
        try {
            List<TaskResponseDto> tasks = ItaskService.searchTasks(keyword);
            return ResponseEntity.ok(new ApiResponse(REQUEST_SUCCESS_MESSAGE, tasks));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(REQUEST_ERROR_MESSAGE, e.getMessage()));
        }
    }
}
