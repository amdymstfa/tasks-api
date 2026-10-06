package com.moustapha.tasks_api.repository;

import com.moustapha.tasks_api.entity.Task;
import com.moustapha.tasks_api.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Find all task by order
     */
    List<Task> findAllByOrderByIdAsc();

    /**
     * Find all task by status
     */
    List<Task> findByTaskStatusOrderByIdAsc(TaskStatus taskStatus);
}