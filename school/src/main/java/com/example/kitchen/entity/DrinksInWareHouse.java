package com.example.kitchen.entity;

import com.example.entity.Branch;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class DrinksInWareHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private int count;

    private int literQuantity;

    private boolean active;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Warehouse warehouse;
}
