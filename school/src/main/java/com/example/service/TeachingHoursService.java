package com.example.service;

import com.example.entity.*;
import com.example.enums.Constants;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.TeachingHoursRequest;
import com.example.model.response.*;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeachingHoursService implements BaseService<TeachingHoursRequest, Integer> {

    private final TeachingHoursRepository teachingHoursRepository;
    private final StudentClassRepository studentClassRepository;
    private final TypeOfWorkRepository typeOfWorkRepository;
    private final UserRepository userRepository;
    private final SubjectLevelRepository subjectLevelRepository;
    private final SalaryRepository salaryRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse create(TeachingHoursRequest teachingHoursRequest) {
        TeachingHours teachingHours = modelMapper.map(teachingHoursRequest, TeachingHours.class);
        checking(teachingHoursRequest);
        setTeachingHours(teachingHoursRequest, teachingHours);
        setSalary(teachingHours);
        teachingHoursRepository.save(teachingHours);
        TeachingHoursResponse response = getTeachingHoursResponse(teachingHours);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        TeachingHours teachingHours = teachingHoursRepository.findByIdAndActiveTrue(integer).orElseThrow(() -> new RecordNotFoundException(Constants.TEACHING_HOURS_NOT_FOUND));
        return new ApiResponse(Constants.SUCCESSFULLY, true, getTeachingHoursResponse(teachingHours));
    }

    public ApiResponse getAll(int page, int size) {
        Page<TeachingHours> all = teachingHoursRepository.findAllByActiveTrue(PageRequest.of(page, size));
        TeachingHoursResponseForPage response = getTeachingHoursResponses(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    public ApiResponse getByTeacherIdAndActiveTrue(Integer id, int page, int size) {
        Page<TeachingHours> all = teachingHoursRepository
                .findAllByTeacherIdAndActiveTrue(id, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        TeachingHoursResponseForPage response = getTeachingHoursResponses(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    public ApiResponse getByTeacherIdAndDate(Integer teacherId, LocalDate startDay, LocalDate finishDay, int page, int size) {
        Page<TeachingHours> all = teachingHoursRepository
                .findAllByTeacherIdAndActiveTrueAndDateBetween(teacherId, startDay, finishDay, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        TeachingHoursResponseForPage response = getTeachingHoursResponses(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse update(TeachingHoursRequest teachingHoursRequest) {
        TeachingHours oldTransaction = teachingHoursRepository.findByIdAndActiveTrue(teachingHoursRequest.getId()).orElseThrow(() -> new RecordNotFoundException(Constants.TEACHING_HOURS_NOT_FOUND));
        TeachingHours teachingHours = modelMapper.map(teachingHoursRequest, TeachingHours.class);
        teachingHours.setId(teachingHoursRequest.getId());
        setTeachingHours(teachingHoursRequest, teachingHours);
        hourlyWageSetting(teachingHours, oldTransaction.getTypeOfWork().getPrice());
        teachingHoursRepository.save(teachingHours);
        return new ApiResponse(Constants.SUCCESSFULLY, true, getTeachingHoursResponse(teachingHours));
    }

    @Override
    public ApiResponse delete(Integer integer) {
        TeachingHours teachingHours = teachingHoursRepository.findById(integer)
                .orElseThrow(() -> new RecordNotFoundException(Constants.TEACHING_HOURS_NOT_FOUND));
        teachingHours.setActive(false);
        rollBackSalary(teachingHours);
        teachingHoursRepository.save(teachingHours);
        return new ApiResponse(Constants.DELETED, true, getTeachingHoursResponse(teachingHours));
    }

    private void hourlyWageSetting(TeachingHours teachingHours, double oldMoney) {

        Salary salary = salaryRepository.findByUserPhoneNumberAndActiveTrue(teachingHours.getTeacher().getPhoneNumber()).orElseThrow(() -> new RecordNotFoundException(Constants.SALARY_NOT_FOUND));

        double debtOrExtra = teachingHours.getTypeOfWork().getPrice() - oldMoney;

        if (debtOrExtra < 0) {

            debtOrExtra = Math.abs(debtOrExtra);

            if (salary.getSalary() > debtOrExtra) {
                salary.setSalary(salary.getSalary() - debtOrExtra);
            } else {
                salary.setAmountDebt(salary.getAmountDebt() + (debtOrExtra - salary.getSalary()));
                salary.setSalary(0);
            }

        } else {
            salary.setSalary(salary.getSalary() + debtOrExtra);
        }
        salaryRepository.save(salary);
    }

    private void rollBackSalary(TeachingHours teachingHours) {

        Salary salary = salaryRepository.findByUserPhoneNumberAndActiveTrue(
                        teachingHours.getTeacher().getPhoneNumber())
                .orElseThrow(() -> new RecordNotFoundException(Constants.SALARY_NOT_FOUND));

        if (salary.getSalary() > teachingHours.getTypeOfWork().getPrice()) {
            salary.setSalary(salary.getSalary() - teachingHours.getTypeOfWork().getPrice());
        } else {
            salary.setAmountDebt(salary.getAmountDebt() + teachingHours.getTypeOfWork().getPrice());
            salary.setAmountDebt(salary.getAmountDebt() - salary.getSalary());
        }
        salaryRepository.save(salary);
    }

    private void setSalary(TeachingHours teachingHours) {
        Salary salary = salaryRepository.findByUserPhoneNumberAndActiveTrue(
                        teachingHours.getTeacher().getPhoneNumber())
                .orElseThrow(() -> new RecordNotFoundException(Constants.SALARY_NOT_FOUND));

        salary.setSalary(salary.getSalary() + teachingHours.getTypeOfWork().getPrice());

        if (salary.getAmountDebt() > 0) {
            if (salary.getSalary() > salary.getAmountDebt()) {
                salary.setSalary(salary.getSalary() - salary.getAmountDebt());
                salary.setAmountDebt(0);
            } else {
                salary.setAmountDebt(salary.getAmountDebt() - salary.getSalary());
                salary.setSalary(0);
            }
        }
        salaryRepository.save(salary);
    }

    private void setTeachingHours(TeachingHoursRequest teachingHoursRequest, TeachingHours teachingHours) {
        StudentClass studentClass = studentClassRepository.findById(teachingHoursRequest.getStudentClassId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.STUDENT_CLASS_NOT_FOUND));
        User user = userRepository.findById(teachingHoursRequest.getTeacherId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.USER_NOT_FOUND));
        TypeOfWork typeOfWork = typeOfWorkRepository.findById(teachingHoursRequest.getTypeOfWorkId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.TYPE_OF_WORK_NOT_FOUND));
        SubjectLevel subject = subjectLevelRepository.findById(teachingHoursRequest.getSubjectLevelId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.SUBJECT_NOT_FOUND));

        teachingHours.setActive(true);
        teachingHours.setStudentClass(studentClass);
        teachingHours.setTeacher(user);
        teachingHours.setTypeOfWork(typeOfWork);
        teachingHours.setSubjectLevel(subject);
    }

    private TeachingHoursResponseForPage getTeachingHoursResponses(Page<TeachingHours> all) {
        TeachingHoursResponseForPage teachingHoursResponseForPage = new TeachingHoursResponseForPage();
        List<TeachingHoursResponse> response = new ArrayList<>();
        all.forEach(teachingHours -> {
            response.add(getTeachingHoursResponse(teachingHours));
        });
        teachingHoursResponseForPage.setTeachingHoursResponses(response);
        teachingHoursResponseForPage.setTotalPage(all.getTotalPages());
        teachingHoursResponseForPage.setTotalElement(all.getTotalElements());
        return teachingHoursResponseForPage;
    }

    private TeachingHoursResponse getTeachingHoursResponse(TeachingHours teachingHours) {
        TeachingHoursResponse response = modelMapper.map(teachingHours, TeachingHoursResponse.class);
        response.setDate(teachingHours.getDate().toString());
        response.setSubjectLevelResponse(modelMapper.map(teachingHours.getSubjectLevel(), SubjectLevelResponse.class));
        response.setTeacher(modelMapper.map(teachingHours.getTeacher(), UserResponse.class));
        response.setStudentClass(modelMapper.map(teachingHours.getStudentClass(), StudentClassResponse.class));
        return response;
    }

    private void checking(TeachingHoursRequest teachingHoursRequest) {
        if (teachingHoursRepository.findByTeacherIdAndDateAndLessonHoursAndActiveTrue(
                        teachingHoursRequest.getTeacherId(),
                        teachingHoursRequest.getDate(),
                        teachingHoursRequest.getLessonHours())
                .isPresent()) {
            throw new RecordAlreadyExistException(Constants.TEACHING_HOURS_ALREADY_EXIST_THIS_DATE);
        }
    }
}