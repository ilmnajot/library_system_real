package com.example.kitchen.model.response;

import com.example.entity.Branch;
import com.example.kitchen.entity.Warehouse;
import lombok.*;

@Data
public class DrinksInWareHouseResponse {

    private Integer id;

    private String name;

    private int count;

    private int literQuantity;

    private boolean active;

    private Branch branch;

    private Warehouse warehouse;

}
