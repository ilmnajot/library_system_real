package com.example.model.request;


import com.example.entity.*;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyLessonsDto {

    private Integer id;

    private int lessonTime;

    private String  day;

    private Integer typeOfWorkId;

    private Integer teacherId;

    private Integer subjectId;

    private Integer studentClassId;

    private Integer branchId;
}
