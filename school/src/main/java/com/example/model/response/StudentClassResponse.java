package com.example.model.response;

import com.example.entity.Branch;
import com.example.entity.Level;
import com.example.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentClassResponse {

    private Integer id;

    private Level level;

    private String className;

    private String startDate;

    private String endDate;

    private boolean active;

    private Room room;

    private Branch branch;

    private UserResponse classLeader;

    private double overallSum;

}
