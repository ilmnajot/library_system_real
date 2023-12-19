package com.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDto {
    private Integer page;
    private Integer size;
    private Integer journalId;
    private Integer subjectId;
    private Integer teacherId;
    private Integer studentId;
    private Integer classId;

}
