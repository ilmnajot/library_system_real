package com.example.kitchen.entity;

import com.example.entity.Branch;
import com.example.enums.MeasurementType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ProductsInWareHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private double quantity;

    private MeasurementType measurementType;

    private boolean active;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Warehouse warehouse;
}