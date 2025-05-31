package Dev.lewi.TaskManagement.dto;

import Dev.lewi.TaskManagement.utils.enums.TaskPriority;
import Dev.lewi.TaskManagement.utils.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
