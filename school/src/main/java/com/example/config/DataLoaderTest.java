package com.example.config;


import com.example.entity.*;
import com.example.enums.Gender;
import com.example.enums.Permissions;
import com.example.kitchen.entity.Warehouse;
import com.example.kitchen.repository.WareHouseRepository;
import com.example.model.request.JournalRequest;
import com.example.repository.*;
import com.example.service.JournalService;
import com.example.service.MainBalanceService;
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
public class DataLoaderTest implements CommandLineRunner {

    private final JournalService journalService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BusinessRepository businessRepository;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final RoomRepository roomRepository;
    private final MainBalanceRepository mainBalanceRepository;
    private final StudentClassRepository studentClassRepository;
    private final StudentRepository studentRepository;
    private final TypeOfWorkRepository typeOfWorkRepository;
    private final SubjectRepository subjectRepository;
    private final LevelRepository levelRepository;
    private final TopicRepository topicRepository;
    private final WareHouseRepository wareHouseRepository;
    private final SubjectLevelRepository subjectLevelRepository;


    @Value("${spring.sql.init.mode}")
    private String initMode;

    @Override
    public void run(String... args) {


        if (initMode.equals("always")) {

            Business business = Business.builder()
                    .name("Demo business")
                    .description("Demo")
                    .phoneNumber("Demo")
                    .active(true)
                    .delete(false)
                    .build();
            Business savedBusiness = businessRepository.save(business);


            Branch branch = Branch.builder()
                    .name("Demo branch")
                    .address("Demo")
                    .business(savedBusiness)
                    .delete(false)
                    .build();

            Role teacher1 = Role.builder().id(2).name("TEACHER").permissions(List.of(Permissions.values())).active(true).build();
            Branch saveBranch = branchRepository.save(branch);

            wareHouseRepository.save(new Warehouse(1, "maktab", true, saveBranch));

            teacher1.setBranch(saveBranch);

            Role savedRole = roleRepository.save(teacher1);

            User teacher = User.builder()
                    .name(" teacher")
                    .surname("teacher")
                    .fatherName("teacher")
                    .phoneNumber("111111112")
                    .birthDate(LocalDate.parse("1998-05-13"))
                    .gender(Gender.ERKAK)
                    .registeredDate(LocalDateTime.now())
                    .verificationCode(0)
                    .password(passwordEncoder.encode("111111"))
                    .blocked(false)
                    .role(savedRole)
                    .branch(saveBranch)
                    .build();
            User savedTeacher = userRepository.save(teacher);


            RoomType roomType = RoomType.builder()
                    .name("O'quv xona")
                    .active(true)
                    .branch(saveBranch)
                    .build();

            RoomType roomType1 = roomTypeRepository.save(roomType);

            Room room = Room.builder()
                    .active(true)
                    .roomNumber(1)
                    .branch(saveBranch)
                    .roomType(roomType1)
                    .build();
            Room room1 = roomRepository.save(room);

            Level savedLavel = levelRepository.getById(6);

            mainBalanceRepository.save(new MainBalance(1, 0, 0, 0, 1, LocalDate.now(), true, saveBranch));


            StudentClass studentClass = StudentClass.builder()
                    .className("1-A sinf")
                    .level(savedLavel)
                    .startDate(LocalDate.parse("2023-05-05"))
                    .endDate(LocalDate.parse("2024-05-05"))
                    .active(true)
                    .room(room1)
                    .createdDate(LocalDateTime.now())
                    .branch(saveBranch)
                    .classLeader(savedTeacher)
                    .build();

            StudentClass savedStudentClass = studentClassRepository.save(studentClass);

            studentRepository.save(new Student(1, "aaa", "a", "a", "907403767", "111111", LocalDate.now(), "wefs", true, 3_000_000, "1", LocalDateTime.now(), null, null, savedStudentClass, saveBranch, null));

            TypeOfWork typeOfWork = TypeOfWork.builder()
                    .branch(saveBranch)
                    .name("dars berish")
                    .price(50000D)
                    .build();
            typeOfWorkRepository.save(typeOfWork);


            Subject matemetika = Subject.builder()
                    .name("Matemetika")
                    .branch(saveBranch)
                    .active(true)
                    .build();
            subjectRepository.save(matemetika);

            SubjectLevel subjectLevel = subjectLevelRepository.save(new SubjectLevel(1, true, matemetika, savedLavel, saveBranch));
            SubjectLevel subject = subjectLevelRepository.save(new SubjectLevel(2, true, matemetika, savedLavel, saveBranch));
            topicRepository.save(new Topic(1, "aaa", null, null, LocalDateTime.now(), subject));
            journalService.create(new JournalRequest(1, 1, 1));
        }
    }
}
