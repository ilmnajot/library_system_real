package sampm.uz.library_system.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

    private HttpStatus httpStatus;
    public BaseException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
    public BaseException(String message){
        super(message);
    }
    public BaseException(HttpStatus httpStatus){
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
