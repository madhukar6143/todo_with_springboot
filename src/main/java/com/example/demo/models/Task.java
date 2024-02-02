package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Text cannot be blank")
    private String text;

    //@NotBlank(message = "Description cannot be blank")
    //@Size(min = 5, max = 100, message = "Description must be between 5 and 100 characters")
    private String description;

    @NotNull(message = "Completed flag cannot be null")
    private Boolean completed;

    @NotNull(message = "Due date cannot be null")
    @FutureOrPresent(message = "Due date must be present or in the future")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "due_date") // Define column name in the database
        private LocalDate dueDate;

}
