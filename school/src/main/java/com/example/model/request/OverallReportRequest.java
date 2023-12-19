package com.example.model.request;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OverallReportRequest {

    private Integer id;

    private LocalDate date;

    private Integer userId;

    private Integer branchId;

    public OverallReportRequest(LocalDate date, Integer userId, Integer branchId) {
        this.date = date;
        this.userId = userId;
        this.branchId = branchId;
    }
}
