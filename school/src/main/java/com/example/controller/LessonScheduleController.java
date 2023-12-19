package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.LessonScheduleRequest;
import com.example.service.LessonScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedule")
public class LessonScheduleController {

    private final LessonScheduleService lessonScheduleService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody LessonScheduleRequest lessonScheduleRequest) {
        return lessonScheduleService.create(lessonScheduleRequest);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return lessonScheduleService.getById(id);
    }

    @PutMapping("/update")
    public ApiResponse update(@RequestBody LessonScheduleRequest lessonScheduleRequest) {
        return lessonScheduleService.update(lessonScheduleRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return lessonScheduleService.delete(id);
    }

    @GetMapping("/getAllByBranchId/{branchId}")
    public ApiResponse getAllActiveClasses(@PathVariable Integer branchId,
                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "5") int size) {
        return lessonScheduleService.getAllByBranchId(branchId, page, size);
    }

    @GetMapping("/getAllByTeacherId/{teacherId}")
    public ApiResponse getAllByTeacherId(@PathVariable Integer teacherId,
                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "5") int size) {
        return lessonScheduleService.getAllByTeacherId(teacherId, page, size);
    }

    @GetMapping("/getAllByStudentClassLevel/{levelId}")
    public ApiResponse getAllByStudentClassLevel(@PathVariable Integer levelId,
                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "5") int size) {
        return lessonScheduleService.getAllByStudentClassLevel(levelId, page, size);
    }
}
