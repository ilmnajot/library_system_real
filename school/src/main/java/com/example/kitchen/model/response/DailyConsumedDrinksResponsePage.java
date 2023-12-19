package com.example.kitchen.model.response;

import lombok.Data;

import java.util.List;

@Data
public class DailyConsumedDrinksResponsePage {

    private List<DailyConsumedDrinksResponse> responseList;
    private long totalElement;
    private int totalPage;
}
