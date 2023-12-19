package com.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RoomRequestDto {

    private Integer roomId;

    private Integer roomNumber;

    private Integer roomTypeId;

    private Integer branchId;
}
