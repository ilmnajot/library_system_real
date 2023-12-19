package com.example.model.response;

import com.example.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponse {
    private Subject subject;
    private List<TopicResponse> topicResponseList;
}
