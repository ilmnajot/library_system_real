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
public class TypeOfWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;// asosiy | to'garaklar | vazifa darslar

    private double price;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Branch branch;
}

