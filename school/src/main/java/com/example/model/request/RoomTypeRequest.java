package com.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeRequest {

    private Integer id;

    private String name;

    private Integer branchId;
}
