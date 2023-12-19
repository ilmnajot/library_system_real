package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.ScoreDto;
import com.example.model.request.ScoreRequest;
import com.example.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/score")
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody ScoreRequest scoreRequest) {
        return scoreService.create(scoreRequest);
    }

    @GetMapping("/getById/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        return scoreService.getById(id);
    }

    @GetMapping("/getAllByJournalId/{journalId}")
    public ApiResponse getAllByJournalId(@PathVariable Integer journalId,
                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "5") int size) {
        return scoreService.getAllByJournalId(journalId, page, size);
    }

    @GetMapping("/getAllByStudentId/{studentId}")
    public ApiResponse getAllByStudentId(@PathVariable Integer studentId,
                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "5") int size) {
        return scoreService.getAllByStudentId(studentId, page, size);
    }

    @GetMapping("/getAllByStudentIdAndSubjectId/{studentId}/{subjectId}")
    public ApiResponse getAllByStudentId(@PathVariable Integer studentId,
                                         @PathVariable Integer subjectId,
                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "5") int size) {
        return scoreService.getAllByStudentIdAndSubjectId(studentId, subjectId, page, size);
    }


    @PutMapping("/update")
    public ApiResponse update(@RequestBody ScoreRequest scoreRequest) {
        return scoreService.update(scoreRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return scoreService.delete(id);
    }
}
