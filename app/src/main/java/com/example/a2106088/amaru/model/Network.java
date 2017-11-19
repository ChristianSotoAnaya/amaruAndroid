package com.example.a2106088.amaru.model;

import com.example.a2106088.amaru.entity.User;

import java.util.List;

/**
 * Created by 2107641 on 10/31/17.
 */

public interface Network
{
    void login(LoginWrapper loginWrapper, RequestCallback<Token> requestCallback);

    void editdescription(final RequestCallback<User> requestCallback, User user);

    void editemail(final RequestCallback<User> requestCallback, User user);

    void editphone(final RequestCallback<User> requestCallback, User user);

    void editimage(final RequestCallback<User> requestCallback, User user);


   /* void createTodo(final RequestCallback<Void> requestCallback, Todo todo);

    void allTodo(RequestCallback<List<Todo>> requestCallback);*/

    void getuser( RequestCallback<User> requestCallback, String username);

}



