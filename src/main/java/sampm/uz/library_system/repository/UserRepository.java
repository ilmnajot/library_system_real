package sampm.uz.library_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sampm.uz.library_system.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
