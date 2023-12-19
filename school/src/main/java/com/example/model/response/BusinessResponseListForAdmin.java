package com.example.model.response;

import com.example.entity.Business;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessResponseListForAdmin {
    private List<Business> businessesResponseDtoList;
    private long allSize;
    private int allPage;
    private int currentPage;
}
