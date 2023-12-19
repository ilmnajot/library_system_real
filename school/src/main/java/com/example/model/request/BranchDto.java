package com.example.model.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BranchDto {

    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @Min(value = 1)
    private Integer businessId;
}
