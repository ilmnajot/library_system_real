package com.example.kitchen.model.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductsInWareHouseResponsePage {
    private List<ProductsInWareHouseResponse> productsInWareHouseResponses;
    private long totalElement;
    private int totalPage;
}
