package com.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScoreRequest {

    private Integer id;

    private int score;

    private String description;

    private Integer studentId;

    private Integer teacherId;

    private Integer subjectLevelId;

    private Integer journalId;
}
