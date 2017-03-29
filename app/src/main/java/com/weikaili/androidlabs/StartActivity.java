package com.weikaili.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "StartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME,"In onCreate()");
        Button button = (Button)findViewById(R.id.button);
        Button toolbar = (Button)findViewById(R.id.toolbar1);
        toolbar.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                    Intent intnt = new Intent(StartActivity.this, TestToolbar.class);
                startActivity(intnt);
            }
        });
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                Intent ListItemsIntent = new Intent(StartActivity.this,ListItemsActivity.class);
                startActivityForResult(ListItemsIntent,5);
            }
        });
        Button startChat = (Button)findViewById(R.id.startChat);
        startChat.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                Intent ChatWindowIntent = new Intent(StartActivity.this,ChatWindow.class );
                startActivity(ChatWindowIntent);
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
            }
        });
        Button weather = (Button)findViewById(R.id.weather);
        weather.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view) {
                Intent WeatherIntent = new Intent(StartActivity.this, WeatherForecast.class);
                startActivity(WeatherIntent);
                // Log.i(ACTIVITY_NAME, "User clicked Start Chat");
            }
        });
    }
    protected void onActivityResult(int requestCode, int responseCode, Intent data){
        if (requestCode == 5){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
        if (responseCode == Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            Log.i(ACTIVITY_NAME,messagePassed);
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(StartActivity.this, messagePassed, duration);
            toast.show();

        }
    }
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");
    }
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
    }
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    }
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }
}
