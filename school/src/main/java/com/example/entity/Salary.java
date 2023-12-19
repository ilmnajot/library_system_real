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
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    private boolean active;

    private double fix;

    private double partlySalary;

    private double givenSalary;

    private double salary;

    private double cashAdvance;

    private double amountDebt;

    private double classLeaderSalary;

    @ManyToOne
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Branch branch;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MainBalance mainBalance;

}