package com.example.kitchen.model.response;

import com.example.entity.Branch;
import com.example.kitchen.entity.Warehouse;
import com.example.model.response.UserResponse;
import lombok.*;

@Data
public class PurchasedDrinksResponse {

    private Integer id;

    private String name;

    private int count;

    private double unitPrice;

    private double totalPrice;

    private int literQuantity;

    private String description;

    private boolean delete;

    private String localDateTime;

    private UserResponse employee;

    private Branch branch;

    private Warehouse warehouse;
}
