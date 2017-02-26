package com.example.android.heartbeep;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.EditText;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.widget.TextView;
import android.view.View.OnFocusChangeListener;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.lang.String;
import java.util.Map;
import java.util.HashMap;
import android.content.IntentFilter;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONObject;
import org.json.JSONException;
import com.android.volley.toolbox.JsonObjectRequest;

public class main_activity extends AppCompatActivity implements View.OnClickListener {

    //use for verifying first time running
    final String PREFS_NAME = "prefsFile";

    //Time Interval
    static protected int days = 3;
    //User Passcode
    static protected String passcode = "1234";
    //boolean to tell if settings are being edited
    static protected boolean editing = false;
    //boolean to tell if the app should send out push notifs
    static protected boolean send_notif = true;
    //LIST OF ARRAY STRING PAIRS FOR CONTACTS
    ArrayList<Map<String, String>> contacts = new ArrayList<Map<String, String>>();
    //Adapter for ListView
    SimpleAdapter adapter;

    //BUTTONS!!!

    //add a contact to the string-array
    Button add_contact;
    //toggle switch for push notifications
    Switch push_notif;
    //ListView to display Contact Names and Numbers
    ListView mListView;
    //Edit Text for Name
    EditText name;
    //Edit Text for Number
    EditText number;
    //Edit Text For the Time
    EditText time_select;

    SharedPreferences prefs = null;

    AlertDialog.Builder dlgAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        //Check for first run -> display information
        prefs = getSharedPreferences(PREFS_NAME, 0);

        //Handler Registration
        dlgAlert = new AlertDialog.Builder(this);

        //add contact button
        add_contact = (Button) findViewById(R.id.add);
        add_contact.setOnClickListener(this);

        //toggle push notifications button
        push_notif = (Switch) findViewById(R.id.reminder_switch);
        push_notif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!editing) {
                    return;
                }
                send_notif = isChecked;
            }
        });

        //listview
        mListView = (ListView) findViewById(R.id.list);
        adapter = new SimpleAdapter(this, contacts, android.R.layout.simple_list_item_2,
                new String[]{"First Line", "Second Line"},
                new int[]{android.R.id.text1, android.R.id.text2});
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                dlgAlert.setMessage("Delete Contact Entry?");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setNegativeButton("Cancel", null);
                dlgAlert.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                contacts.remove(pos);
                                adapter.notifyDataSetChanged();
                            }
                        });
                dlgAlert.show();
            }
        });
        //EDIT TEXTS FOR NAMES AND NUMBERS
        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.number);

        //Edit Text For Time Interval
        time_select = (EditText) findViewById(R.id.time_input);

        time_select.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v.getId() == R.id.time_input) {
                    if (!hasFocus) {
                        if (!editing) {
                            return;
                        }
                        String time_text = time_select.getText().toString();
                        if (time_text.length() > 0) {
                            days = Integer.parseInt(time_text);
                        } else {
                            time_select.setText("");
                        }
                    }
                }
            }
        });

        //Send Post Request as 'Plain Text'
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                if (keyguardManager.isKeyguardSecure()) {
                                /* send HTTP post request */
                    RequestQueue queue = Volley.newRequestQueue(context);
                    String url = "http://7b0dee60.ngrok.io/message/heartbeat";
                                    /* HashMap<String, String> params = new HashMap<String,String>();
                                     for(Map<String,String> entry : contacts){
                                         for(String key : entry.keySet()){
                                             String value = entry.get(key);
                                             params.put(key,value);
                                         }
                                     }
                                     params.put("RuoQi", "19176355777");
                                     JsonObjectRequest req = new JsonObjectRequest(url, new JSONObject(params),
                                             new Response.Listener<JSONObject>() {
                                                 @Override
                                                 public void onResponse(JSONObject response) {
                                                     Log.d("GAY","GAY");
                                                 }
                                             }, new Response.ErrorListener() {
                                         @Override
                                         public void onErrorResponse(VolleyError error) {
                                                Log.d("Duck", "Fylan");
                                         }
                                     });
                                     queue.add(req);
                                 }

                             }*/

                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("RESPONSE", response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Log.d("Error Response", response);
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

                            //params.put({"Name":"RuoQi", "Number":"19176355777"});
                            for (Map<String, String> entry : contacts) {
                                for (String key : entry.keySet()) {
                                    String value = entry.get(key);
                                    params.put(key, value);
                                }
                            }
                            return params;
                        }

                    };
                    queue.add(postRequest);
                }
            }
        }, new IntentFilter("android.intent.action.USER_PRESENT"));

    }

    //OnClick Handlers
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //add contact handler
            case R.id.add:
                String name_string = name.getText().toString();
                String num_string = number.getText().toString();

                //field is empty - error
                if (name_string.length() == 0 || num_string.length() == 0) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                    dlgAlert.setMessage("Username/Password Field Cannot Be Blank!");
                    dlgAlert.setTitle("Uh Oh ...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.create().show();
                }
                //add to the contact list
                else {
                    Map<String, String> datum = new HashMap<String, String>(2);
                    datum.put("First Line", name_string);
                    datum.put("Second Line", num_string);
                    contacts.add(datum);
                }

                //clear the edittext fields
                name.setText("");
                number.setText("");

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getBoolean("my_first_time", true)) {
            //IF IT IS THE FIRST RUN....
            prefs.edit().putBoolean("my_first_time", false).commit();
            //pop up dialog telling you information!
            dlgAlert.setMessage("Initial Days Until Notification Have Been Set to 3!");
            dlgAlert.setTitle("Welcome to HeartBeep!");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.create().show();

        }
    }

    //helper function to convert days into milliseconds for server side timer
    public float daysToMilli(int days) {
        if (days == 420) {
            return 15000;
        }
        return (60 * 60 * 24 * 1000 * days);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }
}