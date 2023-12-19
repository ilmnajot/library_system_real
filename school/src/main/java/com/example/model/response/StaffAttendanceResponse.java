package com.example.model.response;

import com.example.entity.Branch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StaffAttendanceResponse {

    private Integer id;

    private boolean cameToWork;

    private String date;

    private String description;

    private UserResponse userResponse;

    private Branch branch;
}
