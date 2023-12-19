package com.example.model.response;

import com.example.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreResponse {

    private Integer id;

    private int score;

    private String description;

    private String createdDate;

    private StudentResponse student;

    private UserResponse teacher;

    private Subject subject;

    private JournalResponse journal;
}
