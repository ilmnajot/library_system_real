package com.example.model.response;

import com.example.entity.Branch;
import com.example.entity.Room;
import com.example.entity.Subject;
import com.example.entity.TypeOfWork;
import com.example.enums.WeekDays;
import lombok.Data;

@Data
public class LessonScheduleResponse {

    private Integer id;

    private Subject subject;

    private UserResponse teacher;

    private StudentClassResponse studentClass;

    private int lessonHour;

    private boolean active;

    private WeekDays date;

    private Branch branch;

    private Room room;

    private TypeOfWork typeOfWork;
}
