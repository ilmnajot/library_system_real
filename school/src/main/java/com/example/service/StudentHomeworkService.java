package com.example.service;

import com.example.entity.*;
import com.example.enums.Constants;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.StudentHomeworkRequest;
import com.example.model.response.StudentHomeworkResponse;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentHomeworkService implements BaseService<StudentHomeworkRequest, Integer> {

    private final StudentHomeworkRepository studentHomeworkRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final SubjectLevelRepository subjectLevelRepository;
    private final StudentClassRepository studentClassRepository;


    @Override
    public ApiResponse create(StudentHomeworkRequest request) {
        checkingStudentHomeworkForExists(request);
        StudentHomework studentHomework = modelMapper.map(request, StudentHomework.class);
        setStudentHomework(request, studentHomework);
        studentHomeworkRepository.save(studentHomework);
        StudentHomeworkResponse response = getStudentHomeworkResponse(studentHomework);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    private StudentHomeworkResponse getStudentHomeworkResponse(StudentHomework studentHomework) {
        StudentHomeworkResponse response = modelMapper.map(studentHomework, StudentHomeworkResponse.class);
        response.setDate(studentHomework.getDate().toString());
        return response;
    }

    @Override
    public ApiResponse getById(Integer integer) {
        StudentHomework studentHomework = getStudentHomework(integer);
        StudentHomeworkResponse response = getStudentHomeworkResponse(studentHomework);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse update(StudentHomeworkRequest request) {
        StudentHomework homework = getStudentHomework(request.getId());
        if (!request.getDate().equals(homework.getDate())
                || request.getLessonHour() != homework.getLessonHour()
                || !request.getStudentClassId().equals(homework.getStudentClass().getId())) {
            checkingStudentHomeworkForExists(request);
        }
        StudentHomework studentHomework = modelMapper.map(request, StudentHomework.class);
        studentHomework.setId(request.getId());
        setStudentHomework(request, studentHomework);
        studentHomeworkRepository.save(studentHomework);
        StudentHomeworkResponse response = getStudentHomeworkResponse(studentHomework);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    public ApiResponse getList() {
        List<StudentHomework> all = studentHomeworkRepository.findAll();
        List<StudentHomeworkResponse> responses = getStudentHomeworkResponses(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, responses);
    }

    private List<StudentHomeworkResponse> getStudentHomeworkResponses(List<StudentHomework> all) {
        List<StudentHomeworkResponse> responses = new ArrayList<>();
        all.forEach(studentHomework -> {
            responses.add(getStudentHomeworkResponse(studentHomework));
        });
        return responses;
    }

    public ApiResponse getListByActive() {
        List<StudentHomework> all = studentHomeworkRepository
                .findAllByActiveTrue(Sort.by(Sort.Direction.DESC, "id"));
        List<StudentHomeworkResponse> studentHomeworkResponses = getStudentHomeworkResponses(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, studentHomeworkResponses);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        StudentHomework studentHomework = getStudentHomework(integer);
        studentHomework.setActive(false);
        studentHomeworkRepository.save(studentHomework);
        StudentHomeworkResponse response = getStudentHomeworkResponse(studentHomework);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    private void setStudentHomework(StudentHomeworkRequest request, StudentHomework studentHomework) {
        User user = userRepository.findByIdAndBlockedFalse(request.getTeacherId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.USER_NOT_FOUND));
        StudentClass studentClass = studentClassRepository.findByIdAndActiveTrue(request.getStudentClassId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.STUDENT_CLASS_NOT_FOUND));
        SubjectLevel subject = subjectLevelRepository.findByIdAndActiveTrue(request.getSubjectLevelId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.SUBJECT_NOT_FOUND));
        Branch branch = branchRepository.findByIdAndDeleteFalse(request.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.BRANCH_NOT_FOUND));

        studentHomework.setActive(true);
        studentHomework.setDate(LocalDate.now());
        studentHomework.setTeacher(user);
        studentHomework.setStudentClass(studentClass);
        studentHomework.setSubjectLevel(subject);
        studentHomework.setBranch(branch);
    }

    private void checkingStudentHomeworkForExists(StudentHomeworkRequest request) {
        if (studentHomeworkRepository.findByDateAndLessonHourAndStudentClassIdAndActiveTrue(
                        request.getDate(),
                        request.getLessonHour(),
                        request.getStudentClassId())
                .isPresent()) {
            throw new RecordAlreadyExistException(Constants.STUDENT_HOMEWORK_ALREADY_EXISTS);
        }
    }

    private StudentHomework getStudentHomework(Integer integer) {
        return studentHomeworkRepository.findByIdAndActiveTrue(integer)
                .orElseThrow(() -> new RecordNotFoundException(Constants.STUDENT_HOMEWORK_NOT_FOUND));
    }
}
