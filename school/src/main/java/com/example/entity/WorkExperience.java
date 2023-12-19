package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String placeOfWork;

    private String position;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User employee;
}
