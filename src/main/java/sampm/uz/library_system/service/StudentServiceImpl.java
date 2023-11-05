package sampm.uz.library_system.service;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sampm.uz.library_system.entity.Student;
import sampm.uz.library_system.entity.StudentBook;
import sampm.uz.library_system.exception.BookException;
import sampm.uz.library_system.model.request.StudentRequest;
import sampm.uz.library_system.model.common.ApiResponse;
import sampm.uz.library_system.model.response.StudentBookResponse;
import sampm.uz.library_system.model.response.StudentResponse;
import sampm.uz.library_system.repository.BookRepository;
import sampm.uz.library_system.repository.StudentBookRepository;
import sampm.uz.library_system.repository.StudentRepository;
import sampm.uz.library_system.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;

    private final StudentBookRepository studentBookRepository;
    public StudentServiceImpl(UserRepository userRepository, StudentRepository studentRepository, ModelMapper modelMapper, BookRepository bookRepository, StudentBookRepository studentBookRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
        this.studentBookRepository = studentBookRepository;
    }

    @Override
    public ApiResponse addStudent(StudentRequest request) {
        Optional<Student> optionalStudent = studentRepository.findStudentByEmailAndGraduatedFalse(request.getEmail());
        if (optionalStudent.isPresent()) {
            return new ApiResponse("there is a student with that email: " + optionalStudent.get(), false, optionalStudent.get());
        }
        return null;
    }

    @Override
    public ApiResponse getStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findStudentByIdAndGraduatedFalse(id);
        if (studentOptional.isPresent()){
            Student student = studentOptional.get();
            student.setGraduated(false);
            StudentResponse studentResponse = modelMapper.map(student, StudentResponse.class);
            return new ApiResponse("success", true, studentResponse);
        }
        throw new BookException("error: there is no student with id: " + id);
    }

    @Override
    public ApiResponse getStudents(int page, int size) {
        Page<Student> students = studentRepository.findAllByGraduatedFalse(Sort.by("id"), PageRequest.of(page, size));
        if (students!=null){
        return new ApiResponse("here list of students", students.map(student -> modelMapper.map(student, StudentResponse.class)));
    }
        throw new BookException("not found");
    }

    @Override
    public List<StudentResponse> getStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(student -> modelMapper.map(student, StudentResponse.class)).collect(Collectors.toList());
    }

    @Override
    public ApiResponse getBookToStudent(Long bookId, Long studentId) {
        if (!bookRepository.existsById(bookId)){
            throw new BookException("book not found");
        }
        if (!studentRepository.existsById(studentId)){
            throw new BookException("student not found");
        }
        StudentBook studentBook = new StudentBook();
        studentBook.setStudent_id(studentId);
        studentBook.setBook_id(bookId);

        StudentBook savedBookToStudent = studentBookRepository.save(studentBook);
        StudentBookResponse studentBookResponse = modelMapper.map(savedBookToStudent, StudentBookResponse.class);
        return new ApiResponse("success", true,studentBookResponse);
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
