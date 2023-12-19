package com.example.kitchen.model.request;

import com.example.enums.MeasurementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsInWareHouseRequest {

    private Integer id;

    private String name;

    private double quantity;

    private double totalPrice;

    private MeasurementType measurementType;

    private Integer branchId;

    private Integer warehouseId;
}
