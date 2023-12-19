package com.example.model.response;

import com.example.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseList {
    private List<Role> content;
    private long allSize;
    private int allPage;
    private int currentPage;
}
