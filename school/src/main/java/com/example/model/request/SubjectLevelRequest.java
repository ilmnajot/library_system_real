package com.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectLevelRequest {
    private Integer id;
    private Integer branchId;
    private Integer levelId;
    private Integer subjectId;
}
