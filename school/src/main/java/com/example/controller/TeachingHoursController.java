package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.TeachingHoursRequest;
import com.example.service.TeachingHoursService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teachingHours/")
public class TeachingHoursController {

    private final TeachingHoursService teachingHoursService;

    @PostMapping("/save")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN') or hasAnyAuthority('SAVE_TARIFF')")
    public ApiResponse save(@RequestBody @Valid TeachingHoursRequest teachingHoursRequest) {
        return teachingHoursService.create(teachingHoursRequest);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return teachingHoursService.getById(id);
    }

    @GetMapping("/getByTeacherIdAndDate/{id}/{startDay}/{finishDay}")
    public ApiResponse getByTeacherIdAndDate(@PathVariable Integer id,
                                             @PathVariable LocalDate startDay,
                                             @PathVariable LocalDate finishDay,
                                             @RequestParam(name = "page", defaultValue = "0") int page,
                                             @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        return teachingHoursService.getByTeacherIdAndDate(id, startDay, finishDay, page, size);
    }

    @GetMapping("/getByTeacherId/{id}")
    public ApiResponse getByTeacherId(@PathVariable Integer id,
                                      @RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "size", defaultValue = "5") int size) {
        return teachingHoursService.getByTeacherIdAndActiveTrue(id, page, size);
    }

    @GetMapping("/getAll")
    public ApiResponse getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "5") int size) {
        return teachingHoursService.getAll(page, size);
    }

    @PutMapping("/update")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN') or hasAnyAuthority('UPDATE_TARIFF')")
    public ApiResponse update(@RequestBody @Valid TeachingHoursRequest teachingHoursRequest) {
        return teachingHoursService.update(teachingHoursRequest);
    }

    @DeleteMapping("/remove/{id}")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN') or hasAnyAuthority('REMOVE_TARIFF')")
    public ApiResponse remove(@PathVariable Integer id) {
        return teachingHoursService.delete(id);
    }
}
