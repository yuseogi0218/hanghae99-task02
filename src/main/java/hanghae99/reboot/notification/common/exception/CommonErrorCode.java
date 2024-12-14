package hanghae99.reboot.notification.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CommonErrorCode implements ErrorCode {
    // 400 BAD_REQUEST 잘못된 요청
    BAD_REQUEST("COMMON_400_01", HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    MISMATCH_PARAMETER_TYPE("COMMON_400_02", HttpStatus.BAD_REQUEST, "%s 파라미터의 타입이 올바르지 않습니다."),

    // 405 METHOD_NOT_ALLOWED 허용하지 않은 Http Method
    METHOD_NOT_ALLOWED("COMMON_405_01", HttpStatus.METHOD_NOT_ALLOWED, "해당 요청에는 지원하지 않은 HTTP 메서드 입니다."),

    // 500 INTERNAL SERVER ERROR 서버 에러
    INTERNAL_SERVER_ERROR("COMMON_500_01", HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러 입니다."),
    UNKNOWN_ERROR("COMMON_500_02", HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생했습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
