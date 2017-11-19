package com.example.a2106088.amaru.services;



import com.example.a2106088.amaru.entity.User;
import com.example.a2106088.amaru.model.LoginWrapper;
import com.example.a2106088.amaru.model.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by 2107641 on 10/31/17.
 */

public interface NetworkService
{

    @POST( "user/login" )
    Call<Token> login(@Body LoginWrapper user);

    @GET( "user/{username}" )
    Call<User> getuser(@Path("username") String username);

    @POST( "user/editImage" )
    Call<User> editImage(@Body User user);

    @POST( "user/editPhone" )
    Call<User> editPhone(@Body User user);

    @POST( "user/editDescription" )
    Call<User> editDescription(@Body User user);

    @POST( "user/editEmail" )
    Call<User> editEmail(@Body User user);

}