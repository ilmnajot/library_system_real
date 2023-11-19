package sampm.uz.library_system.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
public class BookException extends BaseException {
    private HttpStatus httpStatus;
    public BookException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public BookException(String message){
        super(message);
    }
    @Override
    public HttpStatus getHttpStatus() {
        return super.getHttpStatus();
    }
}
