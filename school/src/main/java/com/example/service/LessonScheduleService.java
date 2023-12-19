package com.example.service;

import com.example.entity.*;
import com.example.enums.Constants;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.LessonScheduleRequest;
import com.example.model.response.LessonScheduleResponse;
import com.example.model.response.LessonScheduleResponsePage;
import com.example.model.response.StudentClassResponse;
import com.example.model.response.UserResponse;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class LessonScheduleService implements BaseService<LessonScheduleRequest, Integer> {

    private final BranchRepository branchRepository;
    private final UserRepository userRepository;
    private final SubjectLevelRepository subjectLevelRepository;
    private final RoomRepository roomRepository;
    private final StudentClassRepository studentClassRepository;
    private final LessonScheduleRepository lessonScheduleRepository;
    private final TypeOfWorkRepository typeOfWorkRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse create(LessonScheduleRequest scheduleRequest) {
        checkingLessonSchedule(scheduleRequest);
        LessonSchedule lessonSchedule = modelMapper.map(scheduleRequest, LessonSchedule.class);
        setLessonSchedule(scheduleRequest, lessonSchedule);
        lessonScheduleRepository.save(lessonSchedule);
        LessonScheduleResponse response = getLessonScheduleResponse(lessonSchedule);
        return new ApiResponse(SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        LessonSchedule lessonSchedule = lessonScheduleRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(LESSON_SCHEDULE_NOT_FOUND));
        LessonScheduleResponse response = getLessonScheduleResponse(lessonSchedule);
        return new ApiResponse(SUCCESSFULLY, true, response);
    }


    public ApiResponse update(LessonScheduleRequest lessonScheduleRequest) {
        lessonScheduleRepository.findByIdAndActiveTrue(lessonScheduleRequest.getId())
                .orElseThrow(() -> new RecordNotFoundException(LESSON_SCHEDULE_NOT_FOUND));
        checkingLessonSchedule(lessonScheduleRequest);
        LessonSchedule lessonSchedule = modelMapper.map(lessonScheduleRequest, LessonSchedule.class);
        setLessonSchedule(lessonScheduleRequest, lessonSchedule);
        lessonSchedule.setId(lessonScheduleRequest.getId());
        lessonScheduleRepository.save(lessonSchedule);
        LessonScheduleResponse response = getLessonScheduleResponse(lessonSchedule);
        return new ApiResponse(SUCCESSFULLY, true, response);
    }


    @Override
    public ApiResponse delete(Integer integer) {
        LessonSchedule lessonSchedule = lessonScheduleRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(LESSON_SCHEDULE_NOT_FOUND));
        lessonSchedule.setActive(false);
        lessonScheduleRepository.save(lessonSchedule);
        LessonScheduleResponse response = getLessonScheduleResponse(lessonSchedule);
        return new ApiResponse(DELETED, true, response);
    }


    public ApiResponse getAllByBranchId(Integer integer, int page, int size) {
        Page<LessonSchedule> all = lessonScheduleRepository.findByBranchIdAndActiveTrue(integer, PageRequest.of(page, size));
        LessonScheduleResponsePage scheduleResponsePage = getLessonScheduleResponsePage(all);
        return new ApiResponse(SUCCESSFULLY, true, scheduleResponsePage);
    }

    public ApiResponse getAllByStudentClassLevel(Integer level, int page, int size) {
        Page<LessonSchedule> all = lessonScheduleRepository.findByStudentClassLevel_IdAndActiveTrue(level, PageRequest.of(page, size));
        LessonScheduleResponsePage scheduleResponsePage = getLessonScheduleResponsePage(all);
        return new ApiResponse(SUCCESSFULLY, true, scheduleResponsePage);
    }

    public ApiResponse getAllByTeacherId(Integer teacherId, int page, int size) {
        Page<LessonSchedule> all = lessonScheduleRepository.findByTeacherIdAndActiveTrue(teacherId, PageRequest.of(page, size));
        LessonScheduleResponsePage scheduleResponsePage = getLessonScheduleResponsePage(all);
        return new ApiResponse(SUCCESSFULLY, true, scheduleResponsePage);
    }

    private LessonScheduleResponsePage getLessonScheduleResponsePage(Page<LessonSchedule> all) {
        LessonScheduleResponsePage scheduleResponsePage = new LessonScheduleResponsePage();
        List<LessonScheduleResponse> responses = new ArrayList<>();
        all.forEach(lessonSchedule -> {
            responses.add(getLessonScheduleResponse(lessonSchedule));
        });
        scheduleResponsePage.setLessonScheduleResponseList(responses);
        scheduleResponsePage.setTotalPage(all.getTotalPages());
        scheduleResponsePage.setTotalElement(all.getTotalElements());
        return scheduleResponsePage;
    }

    private LessonScheduleResponse getLessonScheduleResponse(LessonSchedule lessonSchedule) {
        LessonScheduleResponse response = modelMapper.map(lessonSchedule, LessonScheduleResponse.class);
        response.setTeacher(modelMapper.map(lessonSchedule.getTeacher(), UserResponse.class));
        response.setStudentClass(modelMapper.map(lessonSchedule.getStudentClass(), StudentClassResponse.class));
        return response;
    }

    private void checkingLessonSchedule(LessonScheduleRequest lessonScheduleRequest) {

        Optional<LessonSchedule> studentClassOptional = lessonScheduleRepository
                .findByStudentClassIdAndLessonHourAndBranchIdAndDateAndActiveTrue(
                        lessonScheduleRequest.getStudentClassId(),
                        lessonScheduleRequest.getLessonHour(),
                        lessonScheduleRequest.getBranchId(),
                        lessonScheduleRequest.getWeekDays());

        if (studentClassOptional.isPresent()) {
            throw new RecordAlreadyExistException(Constants.STUDENT_CLASS_BUSY);
        }

        Optional<LessonSchedule> teacherOptional = lessonScheduleRepository
                .findByTeacherIdAndLessonHourAndBranchIdAndDateAndActiveTrue(
                        lessonScheduleRequest.getTeacherId(),
                        lessonScheduleRequest.getLessonHour(),
                        lessonScheduleRequest.getBranchId(),
                        lessonScheduleRequest.getWeekDays());

        if (teacherOptional.isPresent()) {
            throw new RecordAlreadyExistException(TEACHER_ALREADY_BUSY);
        }

        Optional<LessonSchedule> roomOptional = lessonScheduleRepository
                .findByRoomIdAndLessonHourAndBranchIdAndDateAndActiveTrue(
                        lessonScheduleRequest.getRoomId(),
                        lessonScheduleRequest.getLessonHour(),
                        lessonScheduleRequest.getBranchId(),
                        lessonScheduleRequest.getWeekDays());

        if (roomOptional.isPresent()) {
            throw new RecordAlreadyExistException(Constants.ROOM_BUSY);
        }
    }

    private void setLessonSchedule(LessonScheduleRequest scheduleRequest, LessonSchedule lessonSchedule) {
        User user = userRepository.findByIdAndBlockedFalse(scheduleRequest.getTeacherId())
                .orElseThrow(() -> new RecordNotFoundException(USER_NOT_FOUND));
        Branch branch = branchRepository.findByIdAndDeleteFalse(scheduleRequest.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        StudentClass studentClass = studentClassRepository.findByIdAndActiveTrue(scheduleRequest.getStudentClassId())
                .orElseThrow(() -> new RecordNotFoundException(CLASS_NOT_FOUND));
        TypeOfWork typeOfWork = typeOfWorkRepository.findById(scheduleRequest.getTypeOfWorkId())
                .orElseThrow(() -> new RecordNotFoundException(TYPE_OF_WORK_NOT_FOUND));
        Room room = roomRepository.findByIdAndActiveTrue(scheduleRequest.getRoomId())
                .orElseThrow(() -> new RecordNotFoundException(ROOM_NOT_FOUND));
        SubjectLevel subject = subjectLevelRepository.findByIdAndActiveTrue(scheduleRequest.getSubjectLevelId())
                .orElseThrow(() -> new RecordNotFoundException(SUBJECT_NOT_FOUND));

        lessonSchedule.setActive(true);
        lessonSchedule.setRoom(room);
        lessonSchedule.setTeacher(user);
        lessonSchedule.setBranch(branch);
        lessonSchedule.setSubjectLevel(subject);
        lessonSchedule.setTypeOfWork(typeOfWork);
        lessonSchedule.setStudentClass(studentClass);
    }
}
