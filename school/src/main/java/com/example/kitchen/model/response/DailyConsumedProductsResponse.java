package com.example.kitchen.model.response;

import com.example.entity.Branch;
import com.example.enums.MeasurementType;
import com.example.kitchen.entity.Warehouse;
import com.example.model.response.UserResponse;
import lombok.*;

@Data
public class DailyConsumedProductsResponse {

    private Integer id;

    private String name;

    private boolean delete;

    private double quantity;

    private String description;

    private String localDateTime;

    private MeasurementType measurementType;

    private UserResponse employee;

    private Branch branch;

    private Warehouse warehouse;
}
