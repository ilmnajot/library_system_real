package com.example.entity;

import com.example.enums.WeekDays;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class LessonSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int lessonHour;

    private boolean active;

    private WeekDays date;

    @ManyToOne
    private SubjectLevel subjectLevel;

    @ManyToOne
    private User teacher;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private StudentClass studentClass;

    @ManyToOne
    private Room room;

    @ManyToOne
    private TypeOfWork typeOfWork;
}
