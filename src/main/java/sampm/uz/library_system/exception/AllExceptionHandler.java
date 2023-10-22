package sampm.uz.library_system.exception;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(BookException.class)
    public HttpEntity<?> handleAppException(BookException bookException, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), bookException.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
