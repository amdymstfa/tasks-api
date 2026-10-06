package com.moustapha.tasks_api.service;

import com.moustapha.tasks_api.dto.CreateTaskRequest;
import com.moustapha.tasks_api.dto.UpdateStatusRequest;
import com.moustapha.tasks_api.entity.Task;
import com.moustapha.tasks_api.entity.TaskStatus;
import com.moustapha.tasks_api.exception.InvalidRequestException;
import com.moustapha.tasks_api.exception.TaskNotFoundException;
import com.moustapha.tasks_api.repository.TaskRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    static final int TITLE_MAX = 120;
    static final int DESCRIPTION_MAX = 200;

    private final TaskRepository repository;

    @Transactional
    public Task create(CreateTaskRequest request) {
        String title = request.title() == null
                ? ""
                : request.title().trim();

        if (title.isEmpty() || title.length() > TITLE_MAX) {
            throw new InvalidRequestException("Please enter a valid title");
        }

        String description = (request.description() == null || request.description().isBlank())
                ? null
                : request.description().trim();

        if (description != null && description.length() > DESCRIPTION_MAX) {
            throw new InvalidRequestException("Please enter a valid description");
        }

        return repository.save(new Task(title, description));
    }

    @Transactional(readOnly = true)
    public List<Task> list(String rawStatus) {
        if (rawStatus == null) {
            return repository.findAllByOrderByIdAsc();
        }

        return repository.findByTaskStatusOrderByIdAsc(
                TaskStatus.parse(rawStatus)
        );
    }

    @Transactional
    public Task updateStatus(Long id, UpdateStatusRequest request) {
        TaskStatus next = TaskStatus.parse(request.status());

        Task task = repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.changeStatus(next);

        return task;
    }
}