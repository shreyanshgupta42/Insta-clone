/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;


public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
//    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
//            .applicationId("myappID")
//            .clientKey("fd8MjZ5zQXeU")
//            .server("http://13.58.246.218/parse/")
//            .build()
//    );


    //below is the new one
    // password is client key
    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("myappID")
            .clientKey("nNTmaDTHNut0")
            .server("http://18.222.48.152/parse/")
            .build()
    );



    //ParseUser.enableAutomaticUser();

    ParseACL defaultACL = new ParseACL();
    defaultACL.setPublicReadAccess(true);
    defaultACL.setPublicWriteAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);

  }
}
