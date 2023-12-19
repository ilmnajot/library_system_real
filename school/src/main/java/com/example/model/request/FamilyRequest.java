package com.example.model.request;

import com.example.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyRequest {

    private Integer id;

    private String fullName;

    private String phoneNumber;

    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private List<Integer> studentIdList;

    private Integer branchId;
}
