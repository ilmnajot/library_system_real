package com.example.service;

import com.example.entity.*;
import com.example.exception.RecordNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.ScoreRequest;
import com.example.model.response.*;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class ScoreService implements BaseService<ScoreRequest, Integer> {

    private final ScoreRepository scoreRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SubjectLevelRepository subjectLevelRepository;
    private final JournalRepository journalRepository;

    @Override
    public ApiResponse create(ScoreRequest scoreRequest) {
        Score score = modelMapper.map(scoreRequest, Score.class);
        setScore(scoreRequest, score);
        scoreRepository.save(score);
        ScoreResponse response = getScoreResponse(score);
        return new ApiResponse(SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse getById(Integer id) {
        Score score = scoreRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(SCORE_NOT_FOUND));
        ScoreResponse response = getScoreResponse(score);
        return new ApiResponse(SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse update(ScoreRequest dto) {
        Score score = scoreRepository.findById(dto.getId())
                .orElseThrow(() -> new RecordNotFoundException(SCORE_NOT_FOUND));
        setScore(dto, score);
        scoreRepository.save(score);
        ScoreResponse response = getScoreResponse(score);
        return new ApiResponse(SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse delete(Integer id) {
        Score score = scoreRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(SCORE_NOT_FOUND));
        scoreRepository.delete(score);
        ScoreResponse response = getScoreResponse(score);
        return new ApiResponse(DELETED, true, response);
    }

    public ApiResponse getAllByJournalId(Integer journalId, int page, int size) {
        Page<Score> all = scoreRepository.
                findAllByJournalId(journalId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        ScoreResponsePage scoreResponsePage = getScoreResponsePage(all);
        return new ApiResponse(SUCCESSFULLY, true, scoreResponsePage);
    }

    public ApiResponse getAllByStudentId(Integer studentId, int page, int size) {
        Page<Score> all = scoreRepository.
                findAllByStudentId(studentId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        ScoreResponsePage scoreResponsePage = getScoreResponsePage(all);
        return new ApiResponse(SUCCESSFULLY, true, scoreResponsePage);
    }

    public ApiResponse getAllByStudentIdAndSubjectId(Integer studentId, Integer subjectId, int page, int size) {
        Page<Score> all = scoreRepository.
                findAllByStudentIdAndSubjectLevelId(studentId, subjectId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
        ScoreResponsePage scoreResponsePage = getScoreResponsePage(all);
        return new ApiResponse(SUCCESSFULLY, true, scoreResponsePage);
    }

    private ScoreResponse getScoreResponse(Score score) {
        ScoreResponse response = modelMapper.map(score, ScoreResponse.class);
        response.setStudent(modelMapper.map(score.getStudent(),StudentResponse.class));
        response.setJournal(modelMapper.map(score.getJournal(),JournalResponse.class));
        response.setTeacher(modelMapper.map(score.getTeacher(), UserResponse.class));
        response.setCreatedDate(score.getCreatedDate().toString());
        return response;
    }

    private void setScore(ScoreRequest scoreRequest, Score score) {
        Student student = studentRepository.findByIdAndActiveTrue(scoreRequest.getStudentId())
                .orElseThrow(() -> new RecordNotFoundException(STUDENT_NOT_FOUND));
        SubjectLevel subject = subjectLevelRepository.findByIdAndActiveTrue(scoreRequest.getSubjectLevelId())
                .orElseThrow(() -> new RecordNotFoundException(SUBJECT_NOT_FOUND));
        User teacher = userRepository.findByIdAndBlockedFalse(scoreRequest.getTeacherId())
                .orElseThrow(() -> new RecordNotFoundException(TEACHER_NOT_FOUND));
        Journal journal = journalRepository.findByIdAndActiveTrue(scoreRequest.getJournalId())
                .orElseThrow(() -> new RecordNotFoundException(JOURNAL_NOT_FOUND));

        score.setScore(scoreRequest.getScore());
        score.setDescription(scoreRequest.getDescription());
        score.setTeacher(teacher);
        score.setJournal(journal);
        score.setSubjectLevel(subject);
        score.setStudent(student);
        score.setCreatedDate(LocalDateTime.now());
    }

    private ScoreResponsePage getScoreResponsePage(Page<Score> all) {
        List<ScoreResponse> scoreResponses = new ArrayList<>();
        ScoreResponsePage scoreResponsePage = new ScoreResponsePage();
        all.forEach(score -> {
            scoreResponses.add(getScoreResponse(score));
        });
        scoreResponsePage.setScoreResponses(scoreResponses);
        scoreResponsePage.setTotalPage(all.getTotalPages());
        scoreResponsePage.setTotalElement(all.getTotalElements());
        return scoreResponsePage;
    }
}
