package com.moustapha.tasks_api.controller;

import com.moustapha.tasks_api.dto.CreateTaskRequest;
import com.moustapha.tasks_api.dto.TaskResponse;
import com.moustapha.tasks_api.dto.UpdateStatusRequest;
import com.moustapha.tasks_api.entity.Task;
import com.moustapha.tasks_api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping
    public ResponseEntity<TaskResponse> create(
            @RequestBody CreateTaskRequest request
    ) {
        Task task = service.create(request);

        return ResponseEntity
                .created(URI.create("/tasks/" + task.getId()))
                .body(TaskResponse.from(task));
    }

    @GetMapping
    public List<TaskResponse> list(
            @RequestParam(required = false) String status
    ) {
        return service.list(status)
                .stream()
                .map(TaskResponse::from)
                .toList();
    }

    @PatchMapping("/{id}/status")
    public TaskResponse updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequest request
    ) {
        return TaskResponse.from(
                service.updateStatus(id, request)
        );
    }
}