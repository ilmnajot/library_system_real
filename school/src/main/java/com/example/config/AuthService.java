package com.example.config;

import com.example.exception.UserNotFoundException;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.enums.Constants.USER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        return userRepository.findByPhoneNumberAndBlockedFalse(phoneNumber).orElseThrow(()->new UserNotFoundException(USER_NOT_FOUND));
    }
}
