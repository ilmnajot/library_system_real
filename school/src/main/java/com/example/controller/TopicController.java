package com.example.controller;

import com.example.model.common.ApiResponse;
import com.example.model.request.TopicRequest;
import com.example.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topic/")
public class TopicController {

    private final TopicService topicService;

    @PostMapping("create")
    public ApiResponse save(@ModelAttribute TopicRequest topicRequest){
        return topicService.create(topicRequest);
    }

    @GetMapping("getById/{id}")
    public ApiResponse getById(@PathVariable Integer id){
        return topicService.getById(id);
    }

    @GetMapping("findAllByBranchId/{branchId}")
    public ApiResponse findAllByBranchId(@PathVariable Integer branchId){
        return topicService.findAllByBranchId(branchId);
    }

    @GetMapping("findAllBySubjectIdAndBranchId")
    public ApiResponse findAllBySubjectIdBranchId(@RequestParam Integer branchId,
                                         @RequestParam Integer subjectId){
        return topicService.findAllBySubjectIdBranchId(subjectId,branchId);
    }


    @PutMapping("update")
    public ApiResponse update(@ModelAttribute TopicRequest topicRequest){
        return topicService.update(topicRequest);
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse delete(@PathVariable Integer id){
        return topicService.delete(id);
    }
}
