package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/level/")
public class LevelController {

    private final LevelRepository levelRepository;

    @GetMapping("getAll")
    public ApiResponse getAll(){
        return new ApiResponse(levelRepository.findAll(),true);
    }
}
