package com.example.model.response;

import com.example.entity.Branch;
import com.example.enums.Gender;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FamilyResponse {

    private Integer id;

    private String fullName;

    private String phoneNumber;

    private String password;

    private String fireBaseToken;

    private String createdDate;

    private Gender gender;

    private boolean active;

    private Branch branch;

    private List<Integer> studentResponses;
}
