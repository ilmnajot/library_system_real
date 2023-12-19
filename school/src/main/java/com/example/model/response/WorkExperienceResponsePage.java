package com.example.model.response;

import lombok.Data;

import java.util.List;

@Data
public class WorkExperienceResponsePage {
    private List<WorkExperienceResponse> workExperienceResponses;
    private long totalElement;
    private int totalPage;
}
