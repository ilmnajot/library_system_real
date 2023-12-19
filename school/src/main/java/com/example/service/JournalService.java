package com.example.service;

import com.example.entity.*;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.JournalRequest;
import com.example.model.response.JournalResponse;
import com.example.model.response.JournalResponsePage;
import com.example.model.response.StudentClassResponse;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class JournalService implements BaseService<JournalRequest, Integer> {

    private final ModelMapper modelMapper;
    private final BranchRepository branchRepository;
    private final JournalRepository journalRepository;
    private final StudentClassRepository studentClassRepository;


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse create(JournalRequest journalRequest) {
        if (journalRepository.existsByStudentClassIdAndActiveTrue(journalRequest.getStudentClassId())) {
            throw new RecordAlreadyExistException(CLASS_ALREADY_HAVE_JOURNAL);
        }
        Journal journal = new Journal();
        setJournal(journalRequest, journal);
        journalRepository.save(journal);
        JournalResponse response = getJournalResponse(journal);
        return new ApiResponse(SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        Journal journal = journalRepository.findByIdAndActiveTrue(integer)
                .orElseThrow(() -> new RecordNotFoundException(JOURNAL_NOT_FOUND));
        JournalResponse response = getJournalResponse(journal);
        return new ApiResponse(SUCCESSFULLY, true, response);
    }

    public ApiResponse getAllByIdBranchIdAndActiveTrue(Integer integer, int page, int size) {
        Page<Journal> journalList = journalRepository
                .findAllByBranchIdAndActiveTrue(integer,PageRequest.of(page,size,
                        Sort.by(Sort.Direction.DESC, "id")));
        JournalResponsePage journalResponsePage = getJournalResponsePage(journalList);
        return new ApiResponse(SUCCESSFULLY, true, journalResponsePage);
    }

    public ApiResponse getAllByBranchId(Integer integer, int page, int size) {
        Page<Journal> journalList = journalRepository
                .findAllByBranchIdAndActiveTrue(integer,PageRequest.of(page,size,
                                Sort.by(Sort.Direction.DESC, "id")));
        JournalResponsePage journalResponsePage = getJournalResponsePage(journalList);
        return new ApiResponse(SUCCESSFULLY, true, journalResponsePage);
    }

    @Override
    public ApiResponse update(JournalRequest journalRequest) {
        if (journalRepository.existsByStudentClassIdAndActiveTrue(journalRequest.getStudentClassId())) {
            throw new RecordAlreadyExistException(CLASS_ALREADY_HAVE_JOURNAL);
        }

        Journal journal = journalRepository.findByIdAndActiveTrue(journalRequest.getId())
                .orElseThrow(() -> new RecordNotFoundException(JOURNAL_NOT_FOUND));
        setJournal(journalRequest, journal);
        journalRepository.save(journal);
        JournalResponse journalResponse = getJournalResponse(journal);
        return new ApiResponse(SUCCESSFULLY, true, journalResponse);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        Journal journal = journalRepository.findByIdAndActiveTrue(integer)
                .orElseThrow(() -> new RecordNotFoundException(JOURNAL_NOT_FOUND));
        journal.setActive(false);
        journalRepository.save(journal);
        JournalResponse journalResponse = getJournalResponse(journal);
        return new ApiResponse(DELETED, true, journalResponse);
    }

    private void setJournal(JournalRequest journalRequest, Journal journal) {
        Branch branch = branchRepository.findByIdAndDeleteFalse(journalRequest.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        StudentClass studentClass = studentClassRepository.findByIdAndActiveTrue(journalRequest.getStudentClassId())
                .orElseThrow(() -> new RecordNotFoundException(CLASS_NOT_FOUND));

        journal.setActive(true);
        journal.setStartDate(LocalDate.now().getYear());
        journal.setEndDate(LocalDate.now().plusYears(1).getYear());
        journal.setBranch(branch);
        journal.setStudentClass(studentClass);
    }

    private JournalResponsePage getJournalResponsePage(Page<Journal> journalList) {
        List<JournalResponse> journalResponses = new ArrayList<>();
        JournalResponsePage journalResponsePage = new JournalResponsePage();
        journalList.forEach(journal -> {
            journalResponses.add(getJournalResponse(journal));
        });
        journalResponsePage.setJournalResponses(journalResponses);
        journalResponsePage.setTotalPage(journalList.getTotalPages());
        journalResponsePage.setTotalElement(journalList.getTotalElements());
        return journalResponsePage;
    }

    private JournalResponse getJournalResponse(Journal journal) {
        JournalResponse response = modelMapper.map(journal, JournalResponse.class);
        StudentClassResponse classResponse = modelMapper.map(journal.getStudentClass(), StudentClassResponse.class);
        response.setStudentClass(classResponse);
        return response;
    }
}
