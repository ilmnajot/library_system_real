package com.example.service;

import com.example.config.jwtConfig.JwtGenerate;
import com.example.entity.*;
import com.example.exception.RecordAlreadyExistException;
import com.example.exception.RecordNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.model.common.ApiResponse;
import com.example.model.request.*;
import com.example.model.response.NotificationMessageResponse;
import com.example.model.response.TokenResponse;
import com.example.model.response.UserResponse;
import com.example.model.response.UserResponsePage;
import com.example.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.enums.Constants.*;


@Service
@RequiredArgsConstructor
public class UserService implements BaseService<UserRegisterDto, Integer> {

    private final SmsService service;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final BranchRepository branchRepository;
    private final SubjectLevelRepository subjectLevelRepository;
    private final AttachmentRepository attachmentRepository;
    private final AuthenticationManager authenticationManager;
    private final FireBaseMessagingService fireBaseMessagingService;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ApiResponse create(UserRegisterDto userRegisterDto) {
        User user = setAndCheckUser(userRegisterDto);
        if (userRegisterDto.getProfilePhotoId() != null) {
            attachmentRepository.findAllById(userRegisterDto.getId()).ifPresent(user::setProfilePhoto);
        }
        userRepository.save(user);
        UserResponse response = toUserResponse(user);
        return new ApiResponse(SUCCESSFULLY, true, response);
    }

    @Override
    public ApiResponse getById(Integer id) {
        User user = getUserById(id);
        return new ApiResponse(SUCCESSFULLY, true, toUserResponse(user));
    }

    @Override
    public ApiResponse update(UserRegisterDto userRegisterDto) {
        getUserById(userRegisterDto.getId());
        User user = setAndCheckUser(userRegisterDto);
        user.setId(userRegisterDto.getId());
        setPhotoIfIsExist(userRegisterDto, user);
        userRepository.save(user);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    @Override
    public ApiResponse delete(Integer integer) {
        User optional = getUserById(integer);
        optional.setBlocked(true);
        userRepository.save(optional);
        return new ApiResponse(DELETED, true);
    }

    public ApiResponse verify(UserVerifyDto userVerifyRequestDto) {
        User user = userRepository.findByPhoneNumberAndVerificationCode(
                        userVerifyRequestDto.getPhoneNumber(),
                        userVerifyRequestDto.getVerificationCode())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        user.setVerificationCode(0); // TODO: 9/4/2023  to identify why there is for verificationCode?
        user.setBlocked(true);
        userRepository.save(user);
        return new ApiResponse(USER_VERIFIED_SUCCESSFULLY, true, new TokenResponse(JwtGenerate.generateAccessToken(user), toUserResponse(user)));
    }

    public ApiResponse login(UserDto userLoginRequestDto) {
        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userLoginRequestDto.getPhoneNumber(), userLoginRequestDto.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authentication);
            User user = (User) authenticate.getPrincipal();
            return new ApiResponse(new TokenResponse(JwtGenerate.generateAccessToken(user), toUserResponse(user)), true);// TODO: 9/4/2023 how token response works?
        } catch (BadCredentialsException e) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
    }

    public ApiResponse forgetPassword(String number) {
        User user = getUserByNumber(number);
        sendSms(getUserByNumber(number).getPhoneNumber(), verificationCodeGenerator());
        return new ApiResponse(SUCCESSFULLY, true, user);
    }

    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse addBlockUserByID(Integer id) {
        User user = getUserById(id);
        user.setBlocked(true);
        userRepository.save(user);
        sendNotificationByToken(user, BLOCKED);
        return new ApiResponse(BLOCKED, true);
    }

    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse openToBlockUserByID(Integer id) {
        User user = getUserById(id);
        user.setBlocked(false);
        userRepository.save(user);
        sendNotificationByToken(user, OPEN);
        return new ApiResponse(OPEN, true);
    }

    public ApiResponse saveFireBaseToken(FireBaseTokenRegisterDto fireBaseTokenRegisterDto) {
        User user = getUserById(fireBaseTokenRegisterDto.getUserId());
        user.setFireBaseToken(fireBaseTokenRegisterDto.getFireBaseToken());
        userRepository.save(user);
        return new ApiResponse(SUCCESSFULLY, true);
    }

    public ApiResponse changePassword(String number, String password) {
        User user = getUserByNumber(number);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return new ApiResponse(user, true);
    }

    public ApiResponse getUserList(int page, int size) {
        Page<User> all = userRepository.findAll(PageRequest.of(page, size));
        List<UserResponse> userResponseList = new ArrayList<>();
        all.forEach(obj -> userResponseList.add(toUserResponse(obj)));
        return new ApiResponse(new UserResponsePage(userResponseList, all.getTotalElements(), all.getTotalPages(), all.getNumber()), true);
    }

    public ApiResponse getUserListByBranchId(int page, int size, Integer branchId) {
        Page<User> all = userRepository.findAllByBranch_IdAndBlockedFalse(branchId, PageRequest.of(page, size));
        List<UserResponse> userResponseList = new ArrayList<>();
        all.forEach(obj -> userResponseList.add(toUserResponse(obj)));
        return new ApiResponse(new UserResponsePage(userResponseList, all.getTotalElements(), all.getTotalPages(), all.getNumber()), true);
    }

    public ApiResponse getUserListByBranchId(Integer branchId) {
        List<User> all = userRepository.findAllByBranch_IdAndBlockedFalse(branchId, Sort.by(Sort.Direction.DESC, "id"));
        List<UserResponse> responseDtoList = new ArrayList<>();
        all.forEach(obj -> responseDtoList.add(toUserResponse(obj)));
        return new ApiResponse(responseDtoList, true);
    }

    public ApiResponse getUserList() {
        List<User> all = userRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        all.forEach(obj -> userResponseList.add(toUserResponse(obj)));
        return new ApiResponse(userResponseList, true);
    }

    public ApiResponse checkUserResponseExistById() {
        User user = checkUserExistByContext();
        return new ApiResponse(toUserResponse(user), true);
    }

    public ApiResponse removeUserFromContext() {
        User user = checkUserExistByContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName().equals(user.getPhoneNumber())) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return new ApiResponse(DELETED, true);
    }

    public User checkUserExistByContext() {
        User user = getUserFromContext();
        return getUserByNumber(user.getPhoneNumber());
    }

    private User getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) { // TODO: 9/6/2023  
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        return (User)authentication.getPrincipal();
    }

    public ApiResponse addSubjectLevel(Integer userId, List<Integer> subjectIds) {
        User user = getUserById(userId);
        user.setSubjectLevels(subjectLevelRepository.findAllById(subjectIds));
        userRepository.save(user);
        return new ApiResponse(SUCCESSFULLY, true, toUserResponse(user));
    }

    public UserResponse toUserResponse(User user) {
        UserResponse response = modelMapper.map(user, UserResponse.class);
<<<<<<< HEAD

        response.setBirthDate(user.getBirthDate().toString());
        response.setRegisteredDate(user.getRegisteredDate().toString());
=======
>>>>>>> 1d5231ba8984944e7db3de370255f2540ebaf528
        response.setProfilePhotoId(user.getProfilePhoto() == null ? null : user.getProfilePhoto().getId());
        response.setBusinessId(user.getBranch() == null ? null : user.getBranch().getBusiness().getId());

        return response;
    }

    public ApiResponse reSendSms(String number) {
        sendSms(number, verificationCodeGenerator());
        return new ApiResponse(SUCCESSFULLY, true);
    }

    private Integer verificationCodeGenerator() {
        Random random = new Random();
        return random.nextInt(1000, 9999);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    public User getUserByNumber(String number) {
        return userRepository.findByPhoneNumberAndBlockedFalse(number).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    private void setPhotoIfIsExist(UserRegisterDto userRegisterDto, User user) {
        if (userRegisterDto.getProfilePhotoId() != null) {
            if (user.getProfilePhoto() != null) {
                attachmentRepository.delete(user.getProfilePhoto());
            }
            attachmentRepository.findAllById(userRegisterDto.getProfilePhotoId()).ifPresent(user::setProfilePhoto);
        }
    }

    private void sendNotificationByToken(User user, String message) {
        NotificationMessageResponse notificationMessageResponse = NotificationMessageResponse.from(user.getFireBaseToken(), message, new HashMap<>());
        fireBaseMessagingService.sendNotificationByToken(notificationMessageResponse);
    }

    private void sendSms(String phoneNumber, Integer verificationCode) {
        service.sendSms(SmsModel.builder()
                .mobile_phone(phoneNumber)
                .message("Cambridge school " + verificationCode + ".")
                .from(4546)
                .callback_url("http://0000.uz/test.php")
                .build());
    }

    private User setAndCheckUser(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByPhoneNumberAndBlockedFalse(userRegisterDto.getPhoneNumber())) {
            throw new RecordAlreadyExistException(PHONE_NUMBER_ALREADY_REGISTERED);
        }
        User user = modelMapper.map(userRegisterDto, User.class);
        String encode = passwordEncoder.encode(userRegisterDto.getPassword());
        Branch branch = branchRepository.findById(userRegisterDto.getBranchId())
                .orElseThrow(() -> new RecordNotFoundException(BRANCH_NOT_FOUND));
        Role role = roleRepository.findById(userRegisterDto.getRoleId())
                .orElseThrow(() -> new RecordNotFoundException(ROLE_NOT_FOUND));
        List<SubjectLevel> subjects = userRegisterDto.getSubjectLevelIdList() == null
                ? null : subjectLevelRepository.findAllById(userRegisterDto.getSubjectLevelIdList());

        user.setBlocked(false);
        user.setRegisteredDate(LocalDateTime.now());
        user.setBranch(branch);
        user.setRole(role);
        user.setPassword(encode);
        user.setSubjectLevels(subjects);
        return user;
    }
}


