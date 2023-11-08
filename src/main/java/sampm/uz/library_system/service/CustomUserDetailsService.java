package sampm.uz.library_system.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sampm.uz.library_system.entity.Student;
import sampm.uz.library_system.exception.StudentException;
import sampm.uz.library_system.repository.StudentRepository;
import sampm.uz.library_system.repository.UserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final StudentRepository studentRepository;

    public CustomUserDetailsService( StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return studentRepository.findByEmail(username).orElseThrow(() -> new StudentException("Could not find"));
    }
}
