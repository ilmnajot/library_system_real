package com.example.kitchen.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchasedDrinksRequest {

    private Integer id;

    private String name;

    private int count;

    private double unitPrice;

    private double totalPrice;

    private int literQuantity;

    private String description;

    private Integer employeeId;

    private Integer branchId;

    private Integer warehouseId;
}
