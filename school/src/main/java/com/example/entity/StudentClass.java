package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class StudentClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Level level;

    private String className;

    private LocalDate startDate;

    private double overallSum;

    private LocalDate endDate;

    private boolean active;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;

    private LocalDateTime createdDate;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Branch branch;

    @OneToOne
    private User classLeader;
}
