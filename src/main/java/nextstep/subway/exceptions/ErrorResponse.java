package nextstep.subway.exceptions;

public class ErrorResponse {

    private String message;
    private int status;
    private String code;


    public static ErrorResponse of(ErrorCode code) {
        final ErrorResponse response = new ErrorResponse();

        response.message = code.getMessage();
        response.status = code.getStatus();
        response.code = code.getCode();

        return response;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }
}
