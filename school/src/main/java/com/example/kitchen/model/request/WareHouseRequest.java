package com.example.kitchen.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WareHouseRequest {

    private Integer id;

    private String name;

    private Integer branchId;
}
