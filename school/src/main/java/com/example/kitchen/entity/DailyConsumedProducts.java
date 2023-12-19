package com.example.kitchen.entity;

import com.example.entity.Branch;
import com.example.entity.User;
import com.example.enums.MeasurementType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class DailyConsumedProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private boolean delete;

    private double quantity;

    private String description;

    private LocalDateTime localDateTime;

    private MeasurementType measurementType;

    @ManyToOne
    private User employee;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Warehouse warehouse;
}
