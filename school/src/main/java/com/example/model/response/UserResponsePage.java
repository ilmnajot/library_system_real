package com.example.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponsePage {
    private List<UserResponse> userResponseList;
    private long allSize;
    private int allPage;
    private int currentPage;
}
