package com.example.kitchen.model.response;

import lombok.Data;

import java.util.List;

@Data
public class PurchasedProductsResponsePage {

    private List<PurchasedProductsResponse> purchasedProductsResponses;

    private long totalElements;

    private int totalPage;
}
