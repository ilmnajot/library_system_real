package com.example.model.response;

import lombok.Data;

import java.util.List;
@Data
public class LessonScheduleResponsePage {
    private List<LessonScheduleResponse> lessonScheduleResponseList;
    private long totalElement;
    private int totalPage;
}
