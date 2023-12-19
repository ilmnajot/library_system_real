package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TeachingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int lessonHours;

    private LocalDate date;

    private boolean active;

    @ManyToOne
    private TypeOfWork typeOfWork;

    @ManyToOne
    private SubjectLevel subjectLevel;

    @ManyToOne
    private User teacher;

    @ManyToOne
    private StudentClass studentClass;
}