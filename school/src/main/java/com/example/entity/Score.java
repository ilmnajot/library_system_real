package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int score;

    private String description;

    private LocalDateTime createdDate;

    @ManyToOne
    private Student student;

    @ManyToOne
    private User teacher;

    @ManyToOne
    private SubjectLevel subjectLevel;

    @ManyToOne
    private Journal journal;
}
