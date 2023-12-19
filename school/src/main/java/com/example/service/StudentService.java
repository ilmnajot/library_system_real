package com.example.service;

import com.example.entity.*;
import com.example.enums.Constants;
import com.example.exception.*;
import com.example.mappers.StudentMapper;
import com.example.model.common.ApiResponse;
import com.example.model.request.FamilyLoginDto;
import com.example.model.request.StudentRequest;
import com.example.model.response.StudentClassResponse;
import com.example.model.response.StudentInfoDto;
import com.example.model.response.StudentResponse;
import com.example.model.response.StudentResponseListForAdmin;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Stream;

import static com.example.enums.Constants.*;

@Service
@RequiredArgsConstructor
public class StudentService implements BaseService<StudentRequest, Integer> {

    private final StudentRepository studentRepository;
    private final AttachmentRepository attachmentRepository;
    private final StudentClassRepository studentClassRepository;
    private final BranchRepository branchRepository;
    private final JournalRepository journalRepository;
    private final ModelMapper modelMapper;
    private final StudentAccountRepository studentAccountRepository;

    @Override
    public ApiResponse create(StudentRequest studentRequest) {
        Optional<Student> byPhoneNumberAndPassword = studentRepository.findByPhoneNumberAndPassword(studentRequest.getPhoneNumber(), studentRequest.getPassword());
        if (byPhoneNumberAndPassword.isPresent()) {
            throw new RecordAlreadyExistException(PHONE_NUMBER_ALREADY_REGISTERED);
        }
        Branch branch = branchRepository.findByIdAndDeleteFalse(studentRequest.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.BRANCH_NOT_FOUND));
        StudentClass studentClass = studentClassRepository.findByIdAndActiveTrue(studentRequest.getStudentClassId())
                .orElseThrow(() -> new RecordNotFoundException(Constants.STUDENT_CLASS_NOT_FOUND));

        Student student = StudentMapper.toEntity(studentRequest, studentClass, branch);
        savePhotosIfExists(studentRequest, student, false);
        studentRepository.save(student);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse getById(Integer integer) {
        Student student = studentRepository.findByIdAndActiveTrue(integer)
                .orElseThrow(() -> new UserNotFoundException(STUDENT_NOT_FOUND));
        return new ApiResponse(SUCCESSFULLY, true, getStudentResponse(student));
    }


    @Override
    @Transactional(rollbackFor = {FileNotFoundException.class, UserNotFoundException.class, FileInputException.class, OriginalFileNameNullException.class, InputException.class, RecordNotFoundException.class})
    public ApiResponse update(StudentRequest studentRequest) {

        Branch branch = branchRepository.findById(studentRequest.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));

        StudentClass studentClass = studentClassRepository.findById(studentRequest.getStudentClassId())
                .orElseThrow(() -> new RecordNotFoundException(CLASS_NOT_FOUND));


        Student old = studentRepository.findByIdAndActiveTrue(studentRequest.getId())
                .orElseThrow(() -> new UserNotFoundException(STUDENT_NOT_FOUND));

        Student student = StudentMapper.update(studentRequest, studentClass, branch, old);
        student.setAccountNumber(old.getAccountNumber());
        student.setId(old.getId());
        savePhotosIfExists(studentRequest, student, true);
        studentRepository.save(student);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    private void savePhotosIfExists(StudentRequest studentRequest, Student student, boolean isUpdate) {
        if (studentRequest.getPhotoId() != null) {
            if (student.getPhoto() != null && isUpdate)
                attachmentRepository.delete(student.getPhoto());
            attachmentRepository.findAllById(studentRequest.getPhotoId()).ifPresent(student::setPhoto);
        }


        if (studentRequest.getMedDocPhotoId() != null) {
            if (student.getMedDocPhoto() != null && isUpdate)
                attachmentRepository.delete(student.getMedDocPhoto());
            attachmentRepository.findById(studentRequest.getMedDocPhotoId()).ifPresent(student::setMedDocPhoto);
        }

        if (studentRequest.getDocPhotoIds() != null) {
            if (student.getMedDocPhoto() != null && isUpdate)
                attachmentRepository.deleteAll(student.getDocPhoto());
            student.setDocPhoto(attachmentRepository.findAllById(studentRequest.getDocPhotoIds()));
        }
    }

    @Override
    public ApiResponse delete(Integer integer) {
        Student student = studentRepository.findByIdAndActiveTrue(integer)
                .orElseThrow(() -> new UserNotFoundException(STUDENT_NOT_FOUND));
        student.setActive(false);
        studentRepository.save(student);
        StudentResponse response = getStudentResponse(student);
        return new ApiResponse(DELETED, true, response);
    }

    public ApiResponse getList(int page, int size, int branchId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> students = studentRepository.findAllByBranchIdAndActiveTrue(pageable, branchId);
        List<StudentResponse> studentResponseList = new ArrayList<>();
        students.forEach(student -> studentResponseList.add(getStudentResponse(student)));
        studentResponseList.sort(Comparator.comparing(StudentResponse::getLastName).reversed());
        StudentResponseListForAdmin admin = new StudentResponseListForAdmin(
                studentResponseList,
                students.getTotalElements(),
                students.getTotalPages(),
                students.getNumber());
        return new ApiResponse(admin, true);
    }

    private StudentResponse getStudentResponse(Student student) {

        StudentResponse studentResponse = modelMapper.map(student, StudentResponse.class);
        StudentClassResponse classResponse = modelMapper.map(student.getStudentClass(), StudentClassResponse.class);

        studentResponse.setPhotoId(student.getPhoto() == null ? null
                : student.getPhoto().getId());


        if (!student.getDocPhoto().isEmpty() && student.getMedDocPhoto() != null) {
            List<Integer> ids = new ArrayList<>();
            for (Attachment attachment : student.getDocPhoto()) {
                ids.add(attachment.getId());
            }
            studentResponse.setDocPhotoIds(ids);
        }

        studentResponse.setMedDocPhotoId(student.getMedDocPhoto() == null ? null
                : student.getMedDocPhoto().getId());

        studentResponse.setAddedTime(student.getAddedTime().toString());
        studentResponse.setBirthDate(student.getBirthDate().toString());
        studentResponse.setStudentClass(classResponse);
        studentResponse.setBranch(student.getBranch());

        return studentResponse;
    }

    public ApiResponse getListByClassNumber(Integer classId, int branchId) {
        List<Student> students = studentRepository.findAllByStudentClassIdAndBranchIdAndActiveTrue(classId, branchId, Sort.by(Sort.Direction.DESC, "id"));
        List<StudentResponse> studentResponseList = new ArrayList<>();
        students.forEach(student -> studentResponseList.add(getStudentResponse(student)));
        return new ApiResponse(studentResponseList, true);
    }

    public ApiResponse getAllNeActiveStudents(int branchId) {
        List<Student> neActiveStudents = studentRepository.findAllByBranchIdAndActiveFalseOrderByAddedTimeAsc(branchId, Sort.by(Sort.Direction.DESC, "id"));
        List<StudentResponse> studentResponseList = new ArrayList<>();
        neActiveStudents.forEach(student -> studentResponseList.add(getStudentResponse(student)));
        return new ApiResponse(studentResponseList, true);
    }

    public ApiResponse studentLogIn(FamilyLoginDto dto) {
        Student student = studentRepository.findByPhoneNumberAndPassword(dto.getPhoneNumber(), dto.getPassword()).orElseThrow(() -> new RecordNotFoundException(STUDENT_NOT_FOUND));
        Journal journal = journalRepository.findByStudentClassIdAndActiveTrue(student.getStudentClass().getId()).orElseThrow(() -> new RecordNotFoundException(JOURNAL_NOT_FOUND));
        return new ApiResponse(SUCCESSFULLY, true, getStudentResponse(student));
    }

    public ApiResponse search(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Student> search = studentRepository.findAllByFirstNameContainingIgnoreCaseAndActiveTrue(name);
        search.addAll(studentRepository.findAllByLastNameContainingIgnoreCaseAndActiveTrue(name));
        search.addAll(studentRepository.findAllByDocNumberContainingIgnoreCaseAndActiveTrue(name));

        Set<Student> all = new HashSet<>(search);

        search = new ArrayList<>(all);
        List<StudentResponse> arrayList = new ArrayList<>();
        for (Student student : search) {
            arrayList.add(getStudentResponse(student));
        }

        final Page<StudentResponse> studentResponseDtoList = new PageImpl<>(arrayList, pageable, search.size());

        StudentResponseListForAdmin admin = new StudentResponseListForAdmin(
                studentResponseDtoList.stream().toList(),
                studentResponseDtoList.getTotalElements(),
                studentResponseDtoList.getTotalPages(),
                studentResponseDtoList.getNumber());

        return new ApiResponse("all", true, admin);
    }

    public ApiResponse isActiveStudent(Integer id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setActive(false);
            studentRepository.save(student);
<<<<<<< HEAD
        return new ApiResponse(SUCCESSFULLY, true);
=======
            return new ApiResponse("Success", true);
>>>>>>> 1d5231ba8984944e7db3de370255f2540ebaf528
        }
        return new ApiResponse(USER_NOT_FOUND, false);
    }

    public ApiResponse getStudentFilter(Integer classId, int page, int size, boolean isDebt) {
        Optional<StudentClass> optionalStudentClass = studentClassRepository.findById(classId);
        if (optionalStudentClass.isEmpty()) {
            return new ApiResponse("not found class", false);
        }
        Pageable pageable = PageRequest.of(page, size);
        List<StudentInfoDto> studentInfoDtoList = new ArrayList<>();
        List<Student> all = studentRepository.findAllByStudentClassIdAndActiveTrue(classId);
        for (Student student : all) {
            Optional<StudentAccount> optional = studentAccountRepository.findByStudentIdAndActiveTrue(student.getId());
            if (optional.isPresent()) {
                StudentAccount studentAccount = optional.get();
                studentInfoDtoList.add(setStudentInfoDto(student, studentAccount));
            }
        }
        if (isDebt) {
            studentInfoDtoList.sort(Comparator.comparing(StudentInfoDto::getAmountOfDebit).reversed());
        } else {
            studentInfoDtoList.sort(Comparator.comparing(StudentInfoDto::getLastName).reversed());
        }
        Page<StudentInfoDto> allStudentInfo = new PageImpl<>(studentInfoDtoList, pageable, studentInfoDtoList.size());

        Map<String, Object> response = new HashMap<>();
        response.put("allStudent", studentInfoDtoList);
        response.put("currentPage", allStudentInfo.getNumber());
        response.put("totalItem", allStudentInfo.getTotalElements());
        response.put("totalPage", allStudentInfo.getTotalPages());

        return new ApiResponse(Constants.SUCCESSFULLY, true, response);
    }

    private static StudentInfoDto setStudentInfoDto(Student student, StudentAccount studentAccount) {
        StudentInfoDto studentInfoDto = new StudentInfoDto();
        studentInfoDto.setId(student.getId());
        studentInfoDto.setFirstName(student.getFirstName());
        studentInfoDto.setLastName(student.getLastName());
        studentInfoDto.setFatherName(student.getFatherName());
        studentInfoDto.setPhoneNumber(student.getPhoneNumber());
        studentInfoDto.setDocNumber(student.getDocNumber());
        studentInfoDto.setPaymentAmount(student.getPaymentAmount());
        if (student.getPhoto() != null) {
            studentInfoDto.setPhotoId(student.getPhoto().getId());
        }
        studentInfoDto.setAmountOfDebit(studentAccount.getAmountOfDebit());
        studentInfoDto.setBalance(studentAccount.getBalance());
        studentInfoDto.setDiscount(studentAccount.getDiscount());
        studentInfoDto.setPaidInFull(studentAccount.isPaidInFull());
        return studentInfoDto;
    }
}
