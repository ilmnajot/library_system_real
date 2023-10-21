package sampm.uz.library_system.service;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sampm.uz.library_system.entity.Author;
import sampm.uz.library_system.entity.Book;
import sampm.uz.library_system.entity.Student;
import sampm.uz.library_system.model.request.BookRequest;
import sampm.uz.library_system.model.request.StudentRequest;
import sampm.uz.library_system.model.common.ApiResponse;
import sampm.uz.library_system.model.response.BookResponse;
import sampm.uz.library_system.model.response.StudentResponse;
import sampm.uz.library_system.repository.BookRepository;
import sampm.uz.library_system.repository.StudentRepository;
import sampm.uz.library_system.repository.UserRepository;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    public StudentServiceImpl(UserRepository userRepository, StudentRepository studentRepository, ModelMapper modelMapper, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public ApiResponse addStudent(StudentRequest request) {
        Optional<Student> optionalStudent = studentRepository.findStudentByEmailAndAvailableTrue(request.getEmail());
        if (optionalStudent.isPresent()) {
            return new ApiResponse("there is a student with that email: " + optionalStudent.get(), false, optionalStudent.get());
        }
        return null;
    }

    @Override
    public ApiResponse getStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findStudentByIdAndAvailableTrue(id);
        if (studentOptional.isPresent()){
            Student student = studentOptional.get();
            student.setAvailable(true);
            StudentResponse studentResponse = modelMapper.map(student, StudentResponse.class);
            return new ApiResponse("success", true, studentResponse);
        }
        return new ApiResponse("error", false,"there is no student with id: " + id);
    }

    @Override
    public ApiResponse getStudents(int page, int size) {
        Page<Student> students = studentRepository.findAll(PageRequest.of(page, size));
        return new ApiResponse("here list of students", students.map(student -> modelMapper.map(student, StudentResponse.class)));
    }

    @Override
    public ApiResponse getBookToStudent(StudentRequest studentRequest, BookRequest bookRequest) {
       bookRepository.findBookByIsbn(bookRequest.getIsbn());
        if (!optionalBook.isPresent() || optionalBook.get().getBookName().equals(studentRequest.getBooks().stream().map(book -> book.getBookName()))) {
            return new ApiResponse("the student has already taken this book", false, optionalBook.get());
        }
        Book book = optionalBook.get();
        book.setAvailable(true);
        book.setBookName(bookRequest.getBookName());
        book.setIsbn(bookRequest.getIsbn());
        book.setDescription(bookRequest.getDescription());
        book.setCategory(bookRequest.getCategory());
        book.setAvailable(true);
        book.setAuthor(
                Author
                        .builder()
                        .fullName(bookRequest.getAuthor().getFullName())
                        .email(bookRequest.getAuthor().getEmail())
                        .city(bookRequest.getAuthor().getCity())
                        .build());
        book.setStudent(
                Student
                        .builder()
                        .fullName(studentRequest.getFullName())
                        .email(studentRequest.getEmail())
                        .studentGrade(studentRequest.getStudentGrade())
                        .schoolName(studentRequest.getSchoolName())
                        .status(studentRequest.getStatus())
                        .books(studentRequest.getBooks())
                        .role(studentRequest.getRole())
                        .build());
        Book savedBook = bookRepository.save(book);
        BookResponse bookResponse = modelMapper.map(savedBook, BookResponse.class);
        return new ApiResponse("success", true,bookResponse);
    }
    @Override
    public ApiResponse updateStudent(StudentRequest request, Long id) {
        return null;
    }

    @Override
    public ApiResponse deleteStudent(Long id) {
        return null;
    }
}
