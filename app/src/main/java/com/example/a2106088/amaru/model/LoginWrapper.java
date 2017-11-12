package com.example.a2106088.amaru.model;

/**
 * Created by 2107641 on 10/31/17.
 */

public class LoginWrapper
{

    private final String username;

    private final String password;

    public LoginWrapper(String username, String password )
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }
}