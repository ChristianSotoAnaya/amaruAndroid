package com.example.a2106088.amaru.model;

import java.util.List;

/**
 * Created by 2107641 on 10/31/17.
 */

public interface Network
{
    void login(LoginWrapper loginWrapper, RequestCallback<Token> requestCallback);

   /* void createTodo(final RequestCallback<Void> requestCallback, Todo todo);

    void allTodo(RequestCallback<List<Todo>> requestCallback);*/
}



