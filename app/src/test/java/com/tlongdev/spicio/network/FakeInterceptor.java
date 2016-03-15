package com.tlongdev.spicio.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author Long
 * @since 2016. 02. 24.
 */
public class FakeInterceptor implements Interceptor {

    private String responseBody;
    private String contentType;

    public FakeInterceptor(String responseBody, String contentType) {
        this.responseBody = responseBody;
        this.contentType = contentType;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return new Response.Builder()
                .code(200)
                .message(responseBody)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/" + contentType), responseBody.getBytes()))
                .addHeader("content-type", "application/" + contentType)
                .build();
    }
}
