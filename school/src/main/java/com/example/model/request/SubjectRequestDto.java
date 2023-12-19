package com.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRequestDto {

    private Integer id;
    private String name;
    private int branchId;
}
