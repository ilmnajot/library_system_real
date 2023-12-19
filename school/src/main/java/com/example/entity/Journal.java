package com.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean active;

    private int  startDate;

    private int endDate;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StudentClass studentClass;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Branch branch;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Level level;
}