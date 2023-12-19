package com.example.model.response;

import com.example.entity.Level;
import com.example.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectLevelResponse {

    private Integer id;

    private boolean active;

    private Subject subject;

    private Level level;
}
