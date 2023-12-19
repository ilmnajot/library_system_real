package com.example.companent;


import com.example.entity.*;
import com.example.enums.Gender;
import com.example.enums.Permissions;
import com.example.model.request.JournalRequest;
import com.example.repository.*;
import com.example.service.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final LevelRepository levelRepository;


    @Value("${spring.sql.init.mode}")
    private String initMode;

    @Override
    public void run(String... args) {


        if (initMode.equals("always")) {


            Level level1 = new Level(1, 1);
            Level level2 = new Level(2, 2);
            Level level3 = new Level(3, 3);
            Level level4 = new Level(4, 4);
            Level level5 = new Level(5, 5);
            Level level6 = new Level(6, 6);
            Level level7 = new Level(7, 7);
            Level level8 = new Level(8, 8);
            Level level9 = new Level(9, 9);
            Level level10 = new Level(10, 10);
            Level level11 = new Level(11, 11);

            levelRepository.saveAll(List.of(level1, level2, level3, level4, level5, level6, level7, level8, level9, level10, level11));

            Permissions[] permissions = Permissions.values();

            Role supper_admin = Role.builder().id(1).name("SUPER_ADMIN").permissions(List.of(permissions)).active(true).build();

            roleRepository.save(supper_admin);

            User superAdmin = User.builder()
                    .name("Super Admin")
                    .surname("Admin")
                    .fatherName("Admin")
                    .phoneNumber("111111111")
                    .birthDate(LocalDate.parse("1998-05-13"))
                    .gender(Gender.ERKAK)
                    .workDays(30)
                    .registeredDate(LocalDateTime.now())
                    .verificationCode(0)
                    .password(passwordEncoder.encode("111111"))
                    .blocked(false)
                    .role(supper_admin)
                    .build();
            userRepository.save(superAdmin);

        }
    }
}
