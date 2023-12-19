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
public class DailyConsumedProductsRequest {

    private Integer id;

    private String name;

    private double quantity;

    private String description;

    private MeasurementType measurementType;

    private Integer employeeId;

    private Integer branchId;

    private Integer warehouseId;
}
