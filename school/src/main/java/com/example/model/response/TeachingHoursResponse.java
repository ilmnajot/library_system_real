package com.example.model.response;


import com.example.entity.TypeOfWork;
import lombok.Data;

@Data
public class TeachingHoursResponse {

    private Integer id;

    private TypeOfWork typeOfWork;

    private int lessonHours;

    private boolean active;

    private String date;

    private SubjectLevelResponse subjectLevelResponse;

    private UserResponse teacher;

    private StudentClassResponse studentClass;
}
