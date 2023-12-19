package com.example.model.response;

import lombok.Data;

import java.util.List;

@Data
public class AchievementResponsePage {
    private List<AchievementResponse> achievementResponses;
    private long totalElement;
    private int totalPage;
}
