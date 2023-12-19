package com.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicRequest {

    private Integer id;

    private String name;

    private List<Integer> lessonFilesIds;

    private List<String> useFullLinks;

    private Integer subjectLevelId;
}
