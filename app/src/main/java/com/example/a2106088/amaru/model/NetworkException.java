package com.example.a2106088.amaru.model;

import java.io.IOException;

/**
 * Created by 2107641 on 10/31/17.
 */

public class NetworkException extends Exception {


    public NetworkException(Object o, IOException e) {
        super(e);
    }

}
