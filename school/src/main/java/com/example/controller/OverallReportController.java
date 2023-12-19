package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.OverallReportRequest;
import com.example.service.OverallReportService;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/overallReport")
public class OverallReportController {

    private final OverallReportService overallReportService;

    @GetMapping("/getAllByDate")
    public ApiResponse getAllByDate(@RequestParam
                                    @JsonSerialize(using = LocalDateSerializer.class)
                                    @JsonDeserialize(using = LocalDateDeserializer.class)
                                    LocalDate startDate,

                                    @RequestParam
                                    @JsonSerialize(using = LocalDateSerializer.class)
                                    @JsonDeserialize(using = LocalDateDeserializer.class)
                                    LocalDate endDate,

                                    @RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        return overallReportService.getAllByDate(startDate, endDate, page, size);
    }

    @GetMapping("/getAllByBranchId/{branchId}")
    public ApiResponse getAllByDate(
            @PathVariable Integer branchId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        return overallReportService.getAllByBranchId(branchId, page, size);
    }
}
