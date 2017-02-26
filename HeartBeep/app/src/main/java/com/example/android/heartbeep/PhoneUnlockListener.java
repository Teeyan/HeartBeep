package com.example.android.heartbeep;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.app.KeyguardManager;
import android.util.Log;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedOutputStream;


/**
 * Created by JoeTian on 2/26/17.
 */

public class PhoneUnlockListener extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.isKeyguardSecure()) {
            /* send HTTP post request */
            Log.d("Lk","DANKA");

            URL url;
            HttpURLConnection client = null;
            try {
                url = new URL("http://7b0dee60.ngrok.io");
                client = (HttpURLConnection)url.openConnection();
                client.setRequestMethod("POST");
                client.setRequestProperty("Content-Type", "text/plain");
                client.setDoOutput(true);

                String input = "";

                OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());
                //writeStream(outputPost);
                outputPost.flush();
                outputPost.close();

            }catch(IOException e){
                ;
            }

        }
    }
}

