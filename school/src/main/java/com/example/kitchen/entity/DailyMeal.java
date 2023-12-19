package com.example.kitchen.entity;

import com.example.entity.Attachment;
import com.example.entity.Branch;
import com.example.enums.WeekDays;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DailyMeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    private Attachment photo;

    private WeekDays day;

    private String time;

    @ManyToOne
    private Branch branch;
}
