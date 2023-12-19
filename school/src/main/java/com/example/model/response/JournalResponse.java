package com.example.model.response;

import com.example.entity.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class JournalResponse {

    private Integer id;

    private boolean active;

    private int startDate;

    private int endDate;

    private StudentClassResponse studentClass;

    private Branch branch;
}
