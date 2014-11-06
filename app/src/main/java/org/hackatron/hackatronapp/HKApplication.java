package org.hackatron.hackatronapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class HKApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        Parse.initialize(this, "DvzElKZgwWxNrX2KBpVFiL4OOdnnfvLBK3Jk5Dxv", "zGKX8JSF0oc7MiGTBHd1KOoC5j2xV5jpG2hzhndU");


        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
