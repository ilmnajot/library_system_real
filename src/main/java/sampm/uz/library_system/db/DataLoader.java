package sampm.uz.library_system.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sampm.uz.library_system.entity.Author;
import sampm.uz.library_system.entity.Book;
import sampm.uz.library_system.entity.Student;
import sampm.uz.library_system.entity.User;
import sampm.uz.library_system.enums.Category;
import sampm.uz.library_system.enums.SchoolName;
import sampm.uz.library_system.enums.Status;
import sampm.uz.library_system.enums.StudentClass;
import sampm.uz.library_system.repository.AuthorRepository;
import sampm.uz.library_system.repository.BookRepository;
import sampm.uz.library_system.repository.StudentRepository;
import sampm.uz.library_system.repository.UserRepository;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

    public DataLoader(BookRepository bookRepository, StudentRepository studentRepository, UserRepository userRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
    }

    @Value("${spring.sql.init.mode}")
    private String mode;

    @Override
    public void run(String... args) throws Exception {
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
                            //.available(true)
                            .author(null)
                            .build());
            Student student = studentRepository.save(
                    Student
                            .builder()
                            .fullName("student full name")
                            .email("studentgmail@gmail.com")
                            .studentGrade(StudentClass.BLUE_5)
                            .schoolName(SchoolName.SAMARQAND_SHAHRIDAGI_PREZIDENT_MAKTABI)
                            .graduated(false)
                            .status(Status.NOT_IN_DEBT)
                            .books(List.of(book))
                            .build());
            User user = userRepository.save(
                    User
                            .builder()
                            .fullName("user full name")
                            .email("usergmail@gmail.com")
                            .workPlace("workplace")
                            .position("position")
                            .schoolName(SchoolName.SAMARQAND_SHAHRIDAGI_PREZIDENT_MAKTABI)
//                            .available(true)
                            .build());
        }
    }


}
