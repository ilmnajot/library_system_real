package sampm.uz.library_system.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import sampm.uz.library_system.entity.Student;
import sampm.uz.library_system.exception.AuthenticationException;
import sampm.uz.library_system.exception.StudentException;
import sampm.uz.library_system.model.common.ApiResponse;
import sampm.uz.library_system.model.request.LoginRequest;
import sampm.uz.library_system.model.request.StudentRequest;
import sampm.uz.library_system.model.response.LoginResponse;
import sampm.uz.library_system.model.response.StudentResponse;
import sampm.uz.library_system.repository.StudentRepository;
import sampm.uz.library_system.security.jwt.JwtGenerator;

import java.util.Optional;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;

    public AuthServiceImpl(ModelMapper modelMapper, StudentRepository studentRepository, MailService mailService, AuthenticationManager authenticationManager, JwtGenerator jwtGenerator) {
        this.modelMapper = modelMapper;
        this.studentRepository = studentRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public ApiResponse registerStudent(StudentRequest request) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(request.getEmail());
        if (studentByEmail.isPresent()) {
            throw new StudentException("student by email: " + request.getEmail() + " is already registered");
        }
        if (!studentRepository.existsById(request.getRoleId())) {
            throw new StudentException("studentRole is not registered");
        }
        Student student = new Student();
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setPassword(request.getPassword());
        student.setStudentGrade(request.getStudentGrade());
        student.setSchoolName(request.getSchoolName());
        student.setRoleId(request.getRoleId());
        int randomNumber = new Random().nextInt(999999);
        student.setGmailCode(String.valueOf(randomNumber).substring(0, 4));
        mailService.sendMail(student.getEmail(), student.getGmailCode());
        studentRepository.save(student);
        return new ApiResponse("success", true, "4-digit code has been sent to you email address, please verify");
    }

    @Override
    public ApiResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword());
        Authentication authenticated = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        Student principal = (Student) authenticated.getPrincipal();
        String token = jwtGenerator.generateToken(principal.getEmail());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return new ApiResponse("success", loginResponse);
    }
    @Override
    public ApiResponse verifyUser(String email, String emailCode) {
        Optional<Student> optionalStudent = studentRepository.findStudentByEmail(email);
        if (optionalStudent.isEmpty()) {
            throw new StudentException("student not found");
        }
        Student student = optionalStudent.get();
        if (emailCode.equals(student.getGmailCode())) {
            student.setEnabled(true);
            studentRepository.save(student);
            return new ApiResponse("gmail successfully verified", true);
        }
        throw new StudentException("the code you entered is not valid");
    }


}
