package com.example.model.request;

import com.example.enums.Permissions;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class RoleRequestDto {

    private Integer id;

    @NotBlank
    private String name;

    private Integer parentId;

    private Integer branchId;

    private List<Permissions> permissions;
}
