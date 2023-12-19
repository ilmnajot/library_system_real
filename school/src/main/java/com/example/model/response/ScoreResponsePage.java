package com.example.model.response;

import lombok.Data;

import java.util.List;

@Data
public class ScoreResponsePage {
    private List<ScoreResponse> scoreResponses;
    private long totalElement;
    private int totalPage;
}
