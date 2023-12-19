package com.example.kitchen.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyConsumedDrinksRequest {

    private Integer id;

    private String name;

    private int literQuantity;

    private int count;

    private String description;

    private Integer employeeId;

    private Integer branchId;

    private Integer warehouseId;
}
