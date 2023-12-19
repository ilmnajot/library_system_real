package com.example.model.request;

import com.example.enums.WeekDays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonScheduleRequest {

    private Integer id;

    private Integer subjectLevelId;

    private Integer teacherId;

    private Integer branchId;

    private Integer studentClassId;

    private Integer roomId;

    private Integer typeOfWorkId;

    private int lessonHour;

    private WeekDays weekDays;
}
