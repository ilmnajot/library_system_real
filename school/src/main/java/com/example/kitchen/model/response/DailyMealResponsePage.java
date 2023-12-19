package com.example.kitchen.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyMealResponsePage {
    private List<DailyMealResponse> dailyMealResponses;
    private long totalElement;
    private int totalPage;
}
