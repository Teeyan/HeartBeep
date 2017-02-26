package com.example.android.heartbeep;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences;


public class main_activity extends ActionBarActivity {

    //User Passcode
    static protected String passcode = "";

    //User E-Mail
    static protected String username = "";

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        //Check for first run -> display information
        prefs = getSharedPreferences("com.example.android.heartbeep", MODE_PRIVATE);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(prefs.getBoolean("firstrun",true)){
            //IF IT IS THE FIRST RUN....

            //pop up dialog telling you information!




            prefs.edit().putBoolean("firstrun", false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

}
