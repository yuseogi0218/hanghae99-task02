package hanghae99.reboot.notification.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknownException(Exception e) {
        CustomException exception = new CustomException(CommonErrorCode.UNKNOWN_ERROR);
        return exception.toErrorResponse();
    }
    
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        return e.toErrorResponse();
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        CustomException exception = new CustomException(CommonErrorCode.MISMATCH_PARAMETER_TYPE, e.getName());
        return exception.toErrorResponse();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        CustomException exception = new CustomException(CommonErrorCode.METHOD_NOT_ALLOWED);
        return exception.toErrorResponse();
    }
}
