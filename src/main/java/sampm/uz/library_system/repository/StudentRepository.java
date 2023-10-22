package sampm.uz.library_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import sampm.uz.library_system.entity.Student;
import sampm.uz.library_system.enums.Status;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findStudentByEmailAndGraduatedFalse(String email);
    Optional<Student> findStudentByIdAndGraduatedFalse(Long id);
    Page<Student> findAllByGraduatedFalse(Sort sort, PageRequest pageRequest);
    Optional<Student> findStudentByIdAndAvailableTrueAndStatus(Long id, Status status);
    Optional<Student> findStudentByEmailAndAvailableTrue(String email);
    Page<Student> findAllByAvailableTrue(PageRequest pageRequest);
    Page<Student> findAllByAvailableFalse(PageRequest pageRequest);

}
