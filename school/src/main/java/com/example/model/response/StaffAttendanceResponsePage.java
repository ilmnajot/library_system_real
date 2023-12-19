package com.example.model.response;

import lombok.Data;

import java.util.List;

@Data
public class StaffAttendanceResponsePage {
    private List<StaffAttendanceResponse> staffAttendanceResponses;
    private long totalElement;
    private int totalPage;
}
