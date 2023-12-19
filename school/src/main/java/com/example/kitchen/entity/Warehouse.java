package com.example.kitchen.entity;

import com.example.entity.Branch;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private boolean active;

    @ManyToOne
    private Branch branch;
}
