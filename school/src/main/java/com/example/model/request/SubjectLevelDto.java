package com.example.model.request;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectLevelDto {


    private Integer id;

    private Integer subjectId;

    private Integer levelId;

    private int teachingHour;

    private Integer branchId;
}
