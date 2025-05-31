package Dev.lewi.TaskManagement.repository;


import Dev.lewi.TaskManagement.model.Task;
import Dev.lewi.TaskManagement.utils.enums.TaskPriority;
import Dev.lewi.TaskManagement.utils.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByPriority(TaskPriority priority);
    List<Task> findByTitleContainingOrDescriptionContaining(String title, String description);
    List<Task> findByOrderByCreatedAtDesc();
}
