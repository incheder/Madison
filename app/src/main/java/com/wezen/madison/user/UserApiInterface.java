package com.wezen.madison.user;

import com.wezen.madison.utils.ConfigEndpoints;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by eder on 18/02/2016.
 */
public interface UserApiInterface {

    @Headers({ConfigEndpoints.HEADER_APP_ID, ConfigEndpoints.HEADER_REST_API_KEY})
    @GET("/login")
    Observable<User> login(@Query("username") String username, @Query("password") String password);
}
