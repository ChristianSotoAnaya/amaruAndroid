package com.example.a2106088.amaru.model;

/**
 * Created by 2107641 on 10/31/17.
 */

public interface RequestCallback<T>
{

    void onSuccess(T response);

    void onFailed(NetworkException e);

}
