package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.StudentHomeworkRequest;
import com.example.service.StudentHomeworkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/studentHomework")
@RequiredArgsConstructor
public class StudentHomeworkController {

    private final StudentHomeworkService studentHomeworkService;

    @PostMapping("/save")
    public ApiResponse save(@RequestBody @Valid StudentHomeworkRequest studentHomeworkRequest){
        return studentHomeworkService.create(studentHomeworkRequest);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id){
        return studentHomeworkService.getById(id);
    }

    @GetMapping("/getList")
    public ApiResponse getList(){
        return studentHomeworkService.getList();
    }

    @GetMapping("/getListByActive")
    public ApiResponse getListByActive(){
        return studentHomeworkService.getListByActive();
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody StudentHomeworkRequest studentHomeworkRequest){
        return studentHomeworkService.update(studentHomeworkRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id){
        return studentHomeworkService.delete(id);
    }
}
