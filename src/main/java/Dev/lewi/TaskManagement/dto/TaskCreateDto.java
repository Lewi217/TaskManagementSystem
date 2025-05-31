package Dev.lewi.TaskManagement.dto;

import Dev.lewi.TaskManagement.utils.enums.TaskPriority;
import lombok.Data;

@Data
public class TaskCreateDto {
    private String title;
    private String description;
    private TaskPriority priority = TaskPriority.MEDIUM;
}