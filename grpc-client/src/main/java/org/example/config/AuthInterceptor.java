package org.example.config;

import io.grpc.*;
public class AuthInterceptor implements ClientInterceptor {

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
      MethodDescriptor<ReqT, RespT> method,
      CallOptions callOptions,
      Channel next
    ) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
          next.newCall(method, callOptions)
        ) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                // Perform pre-processing logic here
                System.out.println("Performing pre-processing logic...");
                // Add the API key header
                headers.put(
                  Metadata.Key.of("x-api-key",
                    Metadata.ASCII_STRING_MARSHALLER),
                  "secret_api_key"
                );
                // Continue with the call
                super.start(responseListener, headers);
            }


        };
    }

}
