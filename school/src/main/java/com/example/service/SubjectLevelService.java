package com.example.service;

import com.example.entity.Branch;
import com.example.entity.Level;
import com.example.entity.Subject;
import com.example.entity.SubjectLevel;
import com.example.enums.Constants;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.SubjectLevelRequest;
import com.example.model.response.SubjectLevelResponsePage;
import com.example.repository.BranchRepository;
import com.example.repository.LevelRepository;
import com.example.repository.SubjectLevelRepository;
import com.example.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectLevelService implements BaseService<SubjectLevelRequest, Integer> {

    private final SubjectLevelRepository subjectLevelRepository;
    private final SubjectRepository subjectRepository;
    private final LevelRepository levelRepository;
    private final BranchRepository branchRepository;

    @Override
    public ApiResponse create(SubjectLevelRequest subjectLevelRequest) {
        checkingSubjectLevel(subjectLevelRequest);
        SubjectLevel subjectLevel = new SubjectLevel();
        setSubjectLevel(subjectLevelRequest, subjectLevel);
        subjectLevelRepository.save(subjectLevel);
        return new ApiResponse(Constants.SUCCESSFULLY, true, subjectLevel);
    }

    private void checkingSubjectLevel(SubjectLevelRequest subjectLevelRequest) {
        if (subjectLevelRepository.findByBranchIdAndSubjectIdAndLevelIdAndActiveTrue(
                        subjectLevelRequest.getBranchId(),
                        subjectLevelRequest.getSubjectId(),
                        subjectLevelRequest.getLevelId())
                .isPresent()) {
            throw new RecordAlreadyExistException(Constants.SUBJECT_LEVEL_ALREADY_EXISTS);
        }
    }

    @Override
    public ApiResponse getById(Integer integer) {
        SubjectLevel subjectLevel = subjectLevelRepository.findByIdAndActiveTrue(integer)
                .orElseThrow(() -> new RecordNotFoundException(Constants.SUBJECT_LEVEL_NOT_FOUND));
        return new ApiResponse(Constants.SUCCESSFULLY, true, subjectLevel);
    }

    @Override
    public ApiResponse update(SubjectLevelRequest subjectLevelRequest) {
        SubjectLevel subjectLevel = subjectLevelRepository.findByIdAndActiveTrue(subjectLevelRequest.getId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.SUBJECT_LEVEL_NOT_FOUND));
        if (!subjectLevel.getSubject().getId().equals(subjectLevelRequest.getSubjectId())
                || !subjectLevel.getLevel().getId().equals(subjectLevelRequest.getLevelId())
                || !subjectLevel.getBranch().getId().equals(subjectLevelRequest.getBranchId())) {
            checkingSubjectLevel(subjectLevelRequest);
        }
        setSubjectLevel(subjectLevelRequest, subjectLevel);
        subjectLevelRepository.save(subjectLevel);
        return new ApiResponse(Constants.SUCCESSFULLY, true, subjectLevel);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        SubjectLevel subjectLevel = subjectLevelRepository.findByIdAndActiveTrue(integer)
                .orElseThrow(() -> new RecordNotFoundException(Constants.SUBJECT_LEVEL_NOT_FOUND));
        subjectLevelRepository.delete(subjectLevel);
        return new ApiResponse(Constants.DELETED, true, subjectLevel);
    }

    private void setSubjectLevel(SubjectLevelRequest subjectLevelRequest, SubjectLevel subjectLevel) {
        Branch branch = branchRepository.findByIdAndDeleteFalse(subjectLevelRequest.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.BRANCH_NOT_FOUND));
        Level level = levelRepository.findById(subjectLevelRequest.getLevelId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.LEVEL_NOT_FOUND));
        Subject subject = subjectRepository.findByIdAndActiveTrue(subjectLevelRequest.getSubjectId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.SUBJECT_NOT_FOUND));

        subjectLevel.setBranch(branch);
        subjectLevel.setLevel(level);
        subjectLevel.setSubject(subject);
        subjectLevel.setActive(true);
    }

    public ApiResponse getAllSubjectByBranchIdByPage(Integer id, int page, int size) {
        Page<SubjectLevel> all = subjectLevelRepository
                .findAllByBranch_IdAndActiveTrue(id, PageRequest.of(page, size));
        SubjectLevelResponsePage subjectLevelResponsePage = new SubjectLevelResponsePage();
        subjectLevelResponsePage.setSubjectLevels(all.getContent());
        subjectLevelResponsePage.setTotalPage(all.getTotalPages());
        subjectLevelResponsePage.setTotalElement(all.getTotalElements());
        return new ApiResponse(Constants.SUCCESSFULLY, true, subjectLevelResponsePage);
    }

    public ApiResponse getAllSubjectByBranchId(Integer id) {
        List<SubjectLevel> all = subjectLevelRepository
                .findAllByBranchIdAndActiveTrue(id);
        return new ApiResponse(Constants.SUCCESSFULLY, true, all);
    }

}