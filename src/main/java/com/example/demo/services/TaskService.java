package com.example.demo.services;

import com.example.demo.exception.GlobalExceptionHandler;
import com.example.demo.models.Task;
import com.example.demo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task createNewTask(Task task) {
            // Check if a task with the same text already exists
            Optional<Task> existingTask = taskRepository.findByText(task.getText());
            if (existingTask.isPresent())
                throw new GlobalExceptionHandler.OneExceptionHandler("Task with the same text already exists.", HttpStatus.BAD_REQUEST);
            return taskRepository.save(task);
    }

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }



    public List<Task> findAllCompletedTask() {
        return taskRepository.findByCompletedTrue();
    }

    public List<Task> findAllInCompleteTask() {
        return taskRepository.findByCompletedFalse();
    }

    public ResponseEntity<String> deleteTask(Long id) {
        try{
        taskRepository.deleteById(id);
            System.out.println("sjkdhfhius");
                return ResponseEntity.ok("Task deleted successfully");

            } catch (EmptyResultDataAccessException ex) {
                // Handle specific exception for empty result (task not found)
                return ResponseEntity.ok("Failed to delete task: Task does not exist");
        }

    }
    public String updateTask(Task task) {
        // Check if a task with the same text already exists
        Optional<Task> existingTask = taskRepository.findByText(task.getText());

        if (existingTask.isPresent() && !existingTask.get().getId().equals(task.getId())) {
            // A task with the same text already exists
            return "Cannot update because a task with the same text already exists.";
        } else {
            // Update the task
            taskRepository.save(task);
            return "Task updated successfully.";
        }
    }

}
