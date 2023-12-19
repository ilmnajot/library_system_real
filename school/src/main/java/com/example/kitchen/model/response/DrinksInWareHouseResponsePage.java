package com.example.kitchen.model.response;

import lombok.Data;

import java.util.List;

@Data
public class DrinksInWareHouseResponsePage {
    private List<DrinksInWareHouseResponse> drinksInWareHouseResponses;
    private long totalElement;
    private int totalPage;
}
