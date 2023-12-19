package com.example.service;

import com.example.entity.*;
import com.example.enums.Constants;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.OverallReportRequest;
import com.example.model.response.OverallReportResponse;
import com.example.model.response.OverallReportResponsePage;
import com.example.model.response.SalaryResponse;
import com.example.model.response.UserResponse;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OverallReportService {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final SalaryService salaryService;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final SalaryRepository salaryRepository;
    private final StudentClassRepository studentClassRepository;
    private final OverallReportRepository overallReportRepository;

//    @Scheduled(cron = "")
//    public void save() {
//        Page<User> all = userRepository.findAllByBlockedFalse();
//        all.forEach(user -> {
//            create(new OverallReportRequest(LocalDate.now(), user.getId(), user.getBranch().getId()));
//        });
//    }


    public void create(OverallReportRequest overallReportRequest) {
        OverallReport overallReport = modelMapper.map(overallReportRequest, OverallReport.class);
        setOverallReport(overallReportRequest, overallReport);
        overallReportRepository.save(overallReport);
    }


    public ApiResponse getAllByDate(LocalDate startDate, LocalDate endDate, int page, int size) {
        Page<OverallReport> all = overallReportRepository.findAllByDateBetween(startDate, endDate, PageRequest.of(page, size));
        OverallReportResponsePage responses = getOverallReportResponses(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, responses);
    }


    public ApiResponse getAllByBranchId(Integer branchId, int page, int size) {
        Page<OverallReport> all = overallReportRepository.findAllByBranch_Id(branchId, PageRequest.of(page, size));
        OverallReportResponsePage responses = getOverallReportResponses(all);
        return new ApiResponse(Constants.SUCCESSFULLY, true, responses);
    }


    private void setOverallReport(OverallReportRequest overallReportRequest, OverallReport overallReport) {
        User user = userService.getUserById(overallReportRequest.getUserId());
        Salary salary = salaryRepository.findByUserPhoneNumberAndActiveTrue(user.getPhoneNumber())
                .orElseThrow(() -> new RecordNotFoundException(Constants.SALARY_NOT_FOUND));
        Branch branch = branchRepository.findByIdAndDeleteFalse(overallReportRequest.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.BRANCH_NOT_FOUND));
        Optional<StudentClass> studentClassOptional =
                studentClassRepository.findByClassLeaderIdAndActiveTrue(user.getId());

        studentClassOptional.ifPresent(studentClass -> overallReport.setClassLeadership(studentClass.getClassName()));
        overallReport.setBranch(branch);
        overallReport.setSalary(salary);
        overallReport.setUser(user);
    }


    private OverallReportResponse getOverallReportResponse(OverallReport overallReport) {
        OverallReportResponse response = modelMapper.map(overallReport, OverallReportResponse.class);
        UserResponse userResponse = userService.toUserResponse(overallReport.getUser());
        SalaryResponse salaryResponse = salaryService.getResponse(overallReport.getSalary());
        response.setSalary(salaryResponse);
        response.setUserResponse(userResponse);
        response.setDate(overallReport.getDate().toString());
        return response;
    }


    private OverallReportResponsePage getOverallReportResponses(Page<OverallReport> all) {
        OverallReportResponsePage overallReportResponsePage = new OverallReportResponsePage();
        List<OverallReportResponse> allOverallResponse = new ArrayList<>();
        all.forEach(overallReport -> {
            OverallReportResponse overallReportResponse = getOverallReportResponse(overallReport);
            allOverallResponse.add(overallReportResponse);
        });
        overallReportResponsePage.setOverallReportResponses(allOverallResponse);
        overallReportResponsePage.setTotalPage(all.getTotalPages());
        overallReportResponsePage.setTotalElement(all.getTotalElements());
        return overallReportResponsePage;
    }
}
