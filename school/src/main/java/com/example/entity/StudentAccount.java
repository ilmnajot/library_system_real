package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class StudentAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String accountNumber;

    private double amountOfDebit;

    private double balance;

    private Integer discount;

    private boolean paidInFull;

    private LocalDate date;

    private boolean active;

    private String description;

    @ManyToOne
    private MainBalance mainBalance;

    @OneToOne
    private Student student;

    @ManyToOne
    private Branch branch;
}
