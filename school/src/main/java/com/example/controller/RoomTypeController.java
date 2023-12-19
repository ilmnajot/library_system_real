package com.example.controller;

import com.example.entity.RoomType;
import com.example.model.common.ApiResponse;
import com.example.model.request.RoomTypeRequest;
import com.example.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roomType")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody RoomTypeRequest roomType) {
        return roomTypeService.create(roomType);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return roomTypeService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody RoomTypeRequest roomType) {
        return roomTypeService.update(roomType);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return roomTypeService.delete(id);
    }

    @GetMapping("/getAll/{id}")
    public ApiResponse getAll(@PathVariable Integer id) {
        return roomTypeService.getListRoomsByBranchId(id);
    }

}
