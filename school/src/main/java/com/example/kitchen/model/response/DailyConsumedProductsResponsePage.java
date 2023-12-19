package com.example.kitchen.model.response;

import lombok.Data;

import java.util.List;

@Data
public class DailyConsumedProductsResponsePage {

    private List<DailyConsumedProductsResponse> dailyConsumedProductsResponses;
    private long totalElements;
    private int totalPage;
}
