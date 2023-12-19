package com.example.model.response;

import com.example.entity.Branch;
import lombok.Data;

@Data
public class OverallReportResponse {

    private Integer id;

    private String date;

    private String classLeadership;

    private SalaryResponse salary;

    private UserResponse userResponse;

    private Branch branch;
}
