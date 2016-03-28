package com.tlongdev.spicio.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author longi
 * @since 2016.03.28.
 */
public interface SpicioInterface {

    String BASE_URL = "spicio-tlongdev.rhcloud.com/api/v1/";

    @GET("users")
    Call<?> searchUsers(@Query("query") String query);

    @POST("users")
    Call<?> login(@Body Object user);

    @GET("users/{id}")
    Call<?> getUser(@Path("id") long id, @Query("full") boolean full);

    @DELETE("users/{id}")
    Call<?> deleteUser(@Path("id") long id);
}
