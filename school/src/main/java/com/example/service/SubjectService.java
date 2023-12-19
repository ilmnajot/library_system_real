package com.example.service;

import com.example.entity.Branch;
import com.example.entity.Subject;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.SubjectRequestDto;
import com.example.repository.BranchRepository;
import com.example.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class SubjectService implements BaseService<SubjectRequestDto, Integer> {

    private final SubjectRepository subjectRepository;
    private final ModelMapper modelMapper;
    private final BranchRepository branchRepository;

    @Override
    public ApiResponse create(SubjectRequestDto dto) {
        if (subjectRepository.findByName(dto.getName()).isPresent()) {
            throw new RecordAlreadyExistException(SUBJECT_ALREADY_EXIST);
        }
        Subject subject = modelMapper.map(dto, Subject.class);
        setSubject(dto, subject);
        subjectRepository.save(subject);
        return new ApiResponse(SUCCESSFULLY, true, subject);
    }

    @Override
    public ApiResponse getById(Integer subjectId) {
        Subject subject = checkById(subjectId);
        return new ApiResponse(SUCCESSFULLY, true, subject);
    }

    @Override
    public ApiResponse update(SubjectRequestDto subjectRequestDto) {
        Subject subject = checkById(subjectRequestDto.getId());
        setSubject(subjectRequestDto, subject);
        subjectRepository.save(subject);
        return new ApiResponse(SUCCESSFULLY, true, subject);
    }

    @Override
    public ApiResponse delete(Integer id) {
        Subject subject = checkById(id);
        subject.setActive(false);
        subjectRepository.save(subject);
        return new ApiResponse(DELETED, true, subject);
    }

    public ApiResponse getAllSubjectByBranchId(Integer branchId) {
        List<Subject> all = subjectRepository
                .findAllByBranchIdAndActiveTrue(branchId, Sort.by(Sort.Direction.DESC, "id"));
        return new ApiResponse(SUCCESSFULLY, true, all);
    }

    public Subject checkById(Integer id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(SUBJECT_NOT_FOUND));
    }

    private void setSubject(SubjectRequestDto dto, Subject subject) {
        subject.setActive(true);
        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        subject.setBranch(branch);
    }
}
