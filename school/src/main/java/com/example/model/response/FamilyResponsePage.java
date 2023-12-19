package com.example.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyResponsePage {

    private List<FamilyResponse> familyResponseDtoList;
    private long totalElement;
    private int totalPage;
}
