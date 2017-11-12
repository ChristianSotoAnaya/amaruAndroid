package com.example.a2106088.amaru.services;



import com.example.a2106088.amaru.model.LoginWrapper;
import com.example.a2106088.amaru.model.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by 2107641 on 10/31/17.
 */

public interface NetworkService
{

    @POST( "user/login" )
    Call<Token> login(@Body LoginWrapper user);

    /*@POST( "api/todo" )
    Call<Void> createTodo(@Body Todo todo);

    @GET( "api/todo" )
    Call<List<Todo>> allTodo();*/
}