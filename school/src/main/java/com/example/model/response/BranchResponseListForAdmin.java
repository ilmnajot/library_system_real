package com.example.model.response;

import com.example.entity.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchResponseListForAdmin {

    private List<Branch> branchResponseDtoList;
    private long allSize;
    private int allPage;
    private int currentPage;
}
