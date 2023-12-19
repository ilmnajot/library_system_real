package com.example.model.response;

import lombok.Data;

import java.util.List;

@Data
public class TeachingHoursResponseForPage {
    private List<TeachingHoursResponse> teachingHoursResponses;
    private long totalElement;
    private int totalPage;
}
