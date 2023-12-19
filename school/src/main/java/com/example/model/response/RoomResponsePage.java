package com.example.model.response;

import com.example.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponsePage {

    private List<Room> roomResponseDtoList;
    private long allSize;
    private int allPage;
    private int currentPage;
}
