package com.example.model.response;

import lombok.Data;

import java.util.List;

@Data
public class OverallReportResponsePage {
    private List<OverallReportResponse> overallReportResponses;
    private long totalElement;
    private int totalPage;
}
