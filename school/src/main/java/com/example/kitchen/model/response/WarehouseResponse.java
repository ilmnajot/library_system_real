package com.example.kitchen.model.response;

import com.example.kitchen.entity.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseResponse {
    private List<Warehouse> warehouseList;
    private long allSize;
    private int allPage;
    private int currentPage;
}
