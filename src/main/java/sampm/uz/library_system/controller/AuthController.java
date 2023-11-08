package sampm.uz.library_system.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sampm.uz.library_system.model.common.ApiResponse;
import sampm.uz.library_system.model.request.LoginRequest;
import sampm.uz.library_system.model.request.StudentRequest;
import sampm.uz.library_system.model.request.UserRequest;
import sampm.uz.library_system.service.AuthService;
import sampm.uz.library_system.service.UserService;

import static sampm.uz.library_system.utils.Constants.*;

@RestController
@RequestMapping(AUTH)
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }
    @PostMapping(REGISTER_STUDENT)
    public HttpEntity<ApiResponse> registerStudent(@RequestBody StudentRequest request) {
        ApiResponse user = authService.registerStudent(request);
        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @PostMapping(REGISTER_USER)
    public HttpEntity<ApiResponse> registerUser(@RequestBody UserRequest request) {
        ApiResponse user = userService.registerUser(request);
        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping(VERIFY_STUDENT)
    public HttpEntity<ApiResponse> verifyUser(@RequestParam String email, @RequestParam String emailCode) {
        ApiResponse user = authService.verifyUser(email, emailCode);
        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(LOGIN)
    public HttpEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        ApiResponse user = authService.login(request);
        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
