package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class MainBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double balance;

    private double cashBalance;

    private double plasticBalance;

    private int accountNumber;

    private LocalDate date;

    private boolean active;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Branch branch;
}
