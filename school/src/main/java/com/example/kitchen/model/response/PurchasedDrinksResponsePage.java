package com.example.kitchen.model.response;

import lombok.Data;

import java.util.List;

@Data
public class PurchasedDrinksResponsePage {

    private List<PurchasedDrinksResponse> purchasedDrinksResponses;

    private long totalElements;

    private int totalPage;
}
