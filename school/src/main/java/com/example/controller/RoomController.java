package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.RoomRequestDto;
import com.example.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room")
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody RoomRequestDto roomRequestDto) {
        return roomService.create(roomRequestDto);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return roomService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody RoomRequestDto roomRequestDto) {
        return roomService.update(roomRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return roomService.delete(id);
    }

    @GetMapping("/getAll")
    public ApiResponse getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "branchId") Integer branchId) {
        return roomService.getRoomListByBranchId(page, size, branchId);
    }

    @GetMapping("/getAll/{branchId}")
    public ApiResponse getAll(@PathVariable Integer branchId) {
        return roomService.getRoomListByBranchId(branchId);
    }
}