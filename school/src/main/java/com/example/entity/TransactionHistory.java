package com.example.entity;

import com.example.enums.ExpenseType;
import com.example.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double moneyAmount;

    private String comment;

    private LocalDateTime date;

    private boolean active;

    private boolean paidInFull;

    private ExpenseType expenseType;

    private PaymentType paymentType;

    @ManyToOne
    private MainBalance mainBalance;

    @ManyToOne
    private User taker;

    @ManyToOne
    private Student student;

    @OneToOne
    private Branch branch;
}
