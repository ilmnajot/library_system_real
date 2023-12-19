package sampm.uz.library_system.exception;

import org.springframework.http.HttpStatus;

public class TeacherException extends BaseException {
    public TeacherException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
    public TeacherException(String message){
        super(message);
    }
}
