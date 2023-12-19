package com.example.kitchen.model.response;

import com.example.entity.Branch;
import com.example.enums.WeekDays;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyMealResponse {

    private Integer id;

    private String name;

    private Integer photoId;

    private WeekDays day;

    private String time;

    private Branch branch;
}
