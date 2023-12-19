package com.example.controller;

import com.example.annotations.CheckPermission;
import com.example.model.common.ApiResponse;
import com.example.model.request.StudentClassRequest;
import com.example.service.StudentClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/class")
public class StudentClassController {

    private final StudentClassService service;

//    @CheckPermission("ADD_STUDENT_CLASS")
    @PostMapping("/create")
    public ApiResponse create(@RequestBody StudentClassRequest studentClass) {
        return service.create(studentClass);
    }

//    @CheckPermission("GET_STUDENT_CLASS")
    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return service.getById(id);
    }

//    @CheckPermission("EDIT_STUDENT_CLASS")
    @PutMapping("/update")
    public ApiResponse update(@RequestBody StudentClassRequest studentClass) {
        return service.update(studentClass);
    }

//    @CheckPermission("DELETE_STUDENT_CLASS")
    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return service.delete(id);
    }

//    @CheckPermission("GET_STUDENT_CLASS")
    @GetMapping("/getAllActiveClasses/{id}")
    public ApiResponse getAllActiveClasses(@PathVariable Integer id) {
        return service.getAllActiveClasses(id);
    }

//    @CheckPermission("GET_STUDENT_CLASS")
    @GetMapping("/getAllNeActiveClassesByYear/{id}")
    public ApiResponse getAllNeActiveClassesByYear(
            @RequestParam(name = "startDate") LocalDate startDate,
            @RequestParam(name = "endDate") LocalDate endDate,
            @PathVariable Integer id
            ) {
        return service.getAllNeActiveClassesByYear(startDate, endDate,id);
    }
}
