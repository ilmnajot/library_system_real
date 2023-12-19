package com.example.model.response;

import com.example.entity.SubjectLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectLevelResponsePage {
    private List<SubjectLevel> subjectLevels;
    private long totalElement;
    private int totalPage;
}
