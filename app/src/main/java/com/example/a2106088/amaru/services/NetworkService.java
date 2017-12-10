package com.example.a2106088.amaru.services;



import com.example.a2106088.amaru.entity.Comment;
import com.example.a2106088.amaru.entity.Group;
import com.example.a2106088.amaru.entity.Pojo;
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

    @GET( "group/groupsname/{name}" )
    Call<List<Group>> getGrByName(@Path("name") String name);

    @GET( "user/{username}" )
    Call<User> getuser(@Path("username") String username);


    @GET( "group/all" )
    Call<List<Group>> getallgroups();

    @GET( "group/{groupname}" )
    Call<Group> getGroupbyId(@Path("groupname") int groupname);

    @POST( "user/editImage" )
    Call<User> editImage(@Body User user);

    @POST( "user/editPhone" )
    Call<User> editPhone(@Body User user);

    @POST( "user/editDescription" )
    Call<User> editDescription(@Body User user);

    @POST( "user/editEmail" )
    Call<User> editEmail(@Body User user);

    @POST( "user/rate" )
    Call<User> editRate(@Body User user);

    @POST( "user/users" )
    Call<User> createUser(@Body User user);


    @GET( "user/users" )
    Call<List<User>> getUsers();

    @GET( "group/groups/{name}" )
    Call<List<Group>> getCategory(@Path("name") String category);

    @POST("group/groups")
    Call<Group> createGroup(@Body Group group);

    @POST( "group/rate" )
    Call<Group> editRateGroup(@Body Group group);

    @POST("user/buy")
    Call<User> buy(@Body User user);

    @POST("group/susbcribe")
    Call<Boolean> subscribe(@Body Pojo pojo);

    @POST("group/comment")
    Call<Group> addCommentGroup(@Body Comment comment);
}