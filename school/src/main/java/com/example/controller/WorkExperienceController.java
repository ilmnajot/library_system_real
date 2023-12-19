package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.WorkExperienceRequest;
import com.example.service.WorkExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workExperience/")
public class WorkExperienceController {

    private final WorkExperienceService workExperienceService;

    @PostMapping("save")
    public ApiResponse save(@RequestBody @Valid WorkExperienceRequest workExperience) {
        return workExperienceService.create(workExperience);
    }

    @GetMapping("getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return workExperienceService.getById(id);
    }

    @GetMapping("getAllByUserId/{id}")
    public ApiResponse getAllByUserId(@PathVariable Integer id,
                                      @RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "size", defaultValue = "5") int size) {
        return workExperienceService.getAllByUserId(id, page, size);
    }


    @PutMapping("update")
    public ApiResponse update(@RequestBody WorkExperienceRequest workExperience) {
        return workExperienceService.update(workExperience);
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return workExperienceService.delete(id);
    }
}
