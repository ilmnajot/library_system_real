package com.example.entity;

import com.example.enums.Lifetime;
import com.example.enums.Permissions;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Permissions> permissions;

    private int branchAmount;

    private long productAmount;

    private int employeeAmount;

    private Lifetime lifetime;

    private int testDay;

    private int interval;

    private double price;

    private double discount;

    private boolean active;

    private boolean delete;
}
