package com.weikaili.androidlabs;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;
import android.content.Intent;
import android.provider.MediaStore;
import 	android.graphics.Bitmap;

public class ListItemsActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    protected static final String ACTIVITY_NAME = "ListItemsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        ImageButton starButton = (ImageButton)findViewById(R.id.starButton);
        starButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });

        Switch sw = (Switch) findViewById(R.id.Switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CharSequence text = getString(R.string.switchOn);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(ListItemsActivity.this, text, duration);
                    toast.show();
                } else {
                    CharSequence text = getString(R.string.switchOff);
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(ListItemsActivity.this, text, duration);
                    toast.show();
                }
            }

        });
        CheckBox cb = (CheckBox)findViewById(R.id.CheckBox1);
        cb.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("Response","My information to share");
                        setResult(Activity.RESULT_OK,resultIntent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                })
                .show();
            }
        });
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
    @Override
    protected  void  onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageButton starButton = (ImageButton)findViewById(R.id.starButton);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            starButton.setImageBitmap(imageBitmap);
        }
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }
}
