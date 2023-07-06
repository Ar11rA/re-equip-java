package dto.res;

public class ErrorResponse {

    private String message;

    public String getMessage() {
        return message;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public ErrorResponse build() {
        return this;
    }

}
