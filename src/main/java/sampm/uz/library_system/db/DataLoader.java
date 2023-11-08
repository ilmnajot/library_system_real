package sampm.uz.library_system.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sampm.uz.library_system.entity.*;
import sampm.uz.library_system.enums.*;
import sampm.uz.library_system.repository.*;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public DataLoader(BookRepository bookRepository, StudentRepository studentRepository, UserRepository userRepository, AuthorRepository authorRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Value("${spring.sql.init.mode}")
    private String mode;

    @Override
    public void run(String... args) throws Exception {
        Roles dev_role = null;
        if (mode.equals("always")) {
            Author author = authorRepository.save(
                    Author
                            .builder()
                            .fullName("Author name here")
                            .email("author@gmail.com")
                            .city("Samarkand")
                            .bookList(List.of())
                            .build());
            Book book = bookRepository.save(
                    Book
                            .builder()
                            .bookName("O'tkan kunlar")
                            .isbn(123456789101112L)
                            .description("roman, hayotga juda katta yordam beradigan kitob")
                            .category(Category.DRAMA)
                            .count(10)
                            .author(null)
                            .authorId(1L)
                            .build());


            Roles studentRole = roleRepository.save(
                    Roles
                            .builder()
                            .name("student_role")
                            .roleName(RoleName.STUDENT)
                            .deleted(false)
                            .build());
            Roles adminRole = roleRepository.save(
                    Roles
                            .builder()
                            .name("admin_role")
                            .roleName(RoleName.ADMIN)
                            .deleted(false)
                            .build());
            dev_role = roleRepository.save(
                    Roles
                            .builder()
                            .name("dev_role")
                            .roleName(RoleName.DEVELOPER)
                            .deleted(false)
                            .build());

            String encodedPassword = passwordEncoder.encode("1234567890123456789012345678901");
            List<Book> bookList = bookRepository.findAll(Sort.by("id"));
            Student student = studentRepository.save(
                    Student
                            .builder()
                            .fullName("student full name")
                            .email("studentgmail@gmail.com")
                            .passwords(encodedPassword)
                            .studentGrade(StudentClass.BLUE_5)
                            .schoolName(SchoolName.SAMARQAND_SHAHRIDAGI_PREZIDENT_MAKTABI)
                            .graduated(false)
                            .status(Status.NOT_IN_DEBT)
                            .role(studentRole)
                            .numberOfBooks(0)
                            .gender(Gender.MALE)
                            .roleId(1L)
                            .enabled(true)
                            .books(null)
//                            .books(List.of(book))
                            .build());
            String adminPass = passwordEncoder.encode("admin-pass");
            User Admin = userRepository.save(
                    User
                            .builder()
                            .fullName("admin name")
                            .email("admingmail@gmail.com")
                            .password(adminPass)
                            .workPlace("admin workplace")
                            .position("librarian")
                            .schoolName(SchoolName.SAMARQAND_SHAHRIDAGI_PREZIDENT_MAKTABI)
//                            .deleted(false)
                            .role(adminRole)
                            .build());
        }
        User developer = userRepository.save(
                User
                        .builder()
                        .fullName("developer full name")
                        .email("dev@gmail.com")
                        .workPlace("developer workplace here")
                        .position("position")
                        .schoolName(SchoolName.SAMARQAND_SHAHRIDAGI_PREZIDENT_MAKTABI)
//                            .available(true)
//                        .deleted(false)
                        .role(dev_role)
                        .build());


    }
    }



