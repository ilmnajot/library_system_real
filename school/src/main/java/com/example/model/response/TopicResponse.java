package com.example.model.response;

import com.example.entity.SubjectLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicResponse {

    private Integer id;

    private String name;

    private SubjectLevel subjectLevel;

    private List<Integer> lessonFilesId;

    private  List<String> useFullLinks;
}
