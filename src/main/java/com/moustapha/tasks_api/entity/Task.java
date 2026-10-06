package com.moustapha.tasks_api.entity;

import com.moustapha.tasks_api.exception.InvalidTransitionException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(length = 200)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus taskStatus = TaskStatus.TODO;

    public Task(String title, String description){
        this.title = title;
        this.description = description;
    }

    /**
     * Change the status of task is transition allowed
     * @param next the new status
     * @throws InvalidTransitionException if the transaction is not allowed
     */
    public void changeStatus(TaskStatus next){
        if(!this.taskStatus.canTransitionTo(next)){
            throw new InvalidTransitionException(this.taskStatus, next);
        }
        this.taskStatus = next;
    }
}