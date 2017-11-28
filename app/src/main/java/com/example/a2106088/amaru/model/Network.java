package com.example.a2106088.amaru.model;

import com.example.a2106088.amaru.entity.Group;
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

    void editRate(final RequestCallback<User> requestCallback, User user);


   /* void createTodo(final RequestCallback<Void> requestCallback, Todo todo);

    void allTodo(RequestCallback<List<Todo>> requestCallback);*/

    void getuser( RequestCallback<User> requestCallback, String username);

    void getUsers(final RequestCallback<List<User>> requestCallback);

    void getGrByName(final RequestCallback<List<Group>> requestCallback,String name);

    void createUser(final RequestCallback<User> requestCallback, User user);

    void getGroupbyId(final RequestCallback<Group> requestCallback, int groupname);

    void getallgroups(final RequestCallback<List<Group>> requestCallback);

    void createGroup(final RequestCallback<Group> requestCallback, Group group);

    void getcategory(final RequestCallback<List<Group>> requestCallback,String category);

    void editRateGroup(final RequestCallback<Group> requestCallback, Group group);

    void buy(final RequestCallback<User> requestCallback, User user);

}



