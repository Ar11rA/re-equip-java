package providers;

import io.quarkus.logging.Log;

import java.util.concurrent.TimeUnit;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import javax.annotation.Priority;

@Provider
@Priority(2)
public class LoggingInterceptor implements ContainerRequestFilter,
  ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        long requestStartTime = System.nanoTime();
        requestContext.setProperty("requestStartTime", requestStartTime);
    }

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) {
        Object requestStartTime = requestContext.getProperty(
          "requestStartTime");
        if (requestStartTime == null) {
            Log.info(String.format("%s %s returned %s",
                requestContext.getMethod(),
                requestContext.getUriInfo().getPath(),
                responseContext.getStatus()
              )
            );
        } else {
            long requestStartTimeLong = (long) requestStartTime;
            long requestFinishTime = System.nanoTime();
            Log.info(String.format("%s %s returned %s in %d ms",
                requestContext.getMethod(),
                requestContext.getUriInfo().getPath(),
                responseContext.getStatus(),
                TimeUnit.NANOSECONDS.toMillis(requestFinishTime - requestStartTimeLong)
              )
            );
        }
    }
}
