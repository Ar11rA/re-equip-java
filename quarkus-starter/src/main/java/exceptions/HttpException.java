package exceptions;

public class HttpException extends RuntimeException {
    private final int errorCode;

    public HttpException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
