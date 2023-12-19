package com.example.kitchen.model.response;

import com.example.entity.Branch;
import com.example.enums.MeasurementType;
import com.example.kitchen.entity.Warehouse;
import com.example.model.response.UserResponse;
import lombok.*;

@Data
public class PurchasedProductsResponse {

    private Integer id;

    private String name;

    private double quantity;

    private double unitPrice;

    private double totalPrice;

    private boolean delete;

    private String description;

    private String localDateTime;

    private MeasurementType measurementType;

    private UserResponse employee;

    private Branch branch;

    private Warehouse warehouse;

}
