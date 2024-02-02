package com.example.demo.repositories;

import com.example.demo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findByCompletedTrue();
    public List<Task> findByCompletedFalse();


    Optional<Task> findByText(String text);
}
