package sampm.uz.library_system.service;

import sampm.uz.library_system.model.common.ApiResponse;
import sampm.uz.library_system.model.request.LoginRequest;
import sampm.uz.library_system.model.request.StudentRequest;
import sampm.uz.library_system.model.request.UserRequest;

public interface AuthService{
    ApiResponse registerStudent(StudentRequest request);

    ApiResponse verifyUser(String email, String emailCode);

    ApiResponse login(LoginRequest request);
}
