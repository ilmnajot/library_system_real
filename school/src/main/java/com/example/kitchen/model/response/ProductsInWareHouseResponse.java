package com.example.kitchen.model.response;

import com.example.entity.Branch;
import com.example.enums.MeasurementType;
import com.example.kitchen.entity.Warehouse;
import lombok.*;

@Data
public class ProductsInWareHouseResponse {

    private Integer id;

    private String name;

    private double quantity;

    private MeasurementType measurementType;

    private boolean active;

    private Branch branch;

    private Warehouse warehouse;
}