package providers;

import dto.res.ErrorResponse;
import exceptions.HttpException;

import io.quarkus.logging.Log;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CommonExceptionMapper implements ExceptionMapper<HttpException> {

    @Override
    public Response toResponse(HttpException exception) {
        int errorCode = exception.getErrorCode();
        String errorMessage = exception.getMessage();
        Log.error("Failure with message: " + errorMessage, exception);
        ErrorResponse errorResponse = new ErrorResponse()
          .setMessage(errorMessage)
          .build();
        return Response.status(errorCode).entity(errorResponse).build();
    }
}
