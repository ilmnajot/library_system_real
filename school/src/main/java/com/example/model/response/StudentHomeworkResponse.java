package com.example.model.response;

import com.example.entity.Branch;
import com.example.entity.Subject;
import lombok.Data;

@Data
public class StudentHomeworkResponse {

    private Integer id;

    private int topicNumber;

    private int lessonHour;

    private String homework;

    private String description;

    private String date;

    private Subject subject;

    private Integer teacherId;

    private Integer studentClassId;

    private Branch branch;

    private boolean active;
}
