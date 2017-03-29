package com.weikaili.androidlabs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {
   String inp = "You selected item 1";
    private void setInp(String str){
        inp = str;
    }
    private String getInp(){
        return inp;
    }
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }
    public boolean onOptionsItemSelected (MenuItem mi){
        switch(mi.getItemId()){
            case R.id.action_one:
                Log.d("Toolbar",inp);


                        Snackbar.make(findViewById(R.id.fab),getInp() , Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                break;
            case R.id.action_two:
                Log.d("Toolbar","Option 2 selected");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Do you want to go back?");
// Add the buttons
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

// Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.action_three:

                Log.d("Toolbar","Option 3 selected");
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                // Get the layout inflater
                LayoutInflater inflater = this.getLayoutInflater();
                final View inf = inflater.inflate(R.layout.dialog_signin, null);
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder1.setView(inf);

                        // Add action buttons
                       builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //Log.i("Dialog","1111");
                                EditText tx = (EditText) inf.findViewById(R.id.newMsg) ;
                                setInp(tx.getText().toString());
                                Log.i("dialog",getInp());
                                Snackbar.make(findViewById(R.id.fab),getInp() , Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            }
                        });
                        builder1.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                          @Override
                           public void onClick(DialogInterface dialog, int id) {
                               // User cancelled the dialog
                           }
                       });

                 AlertDialog dialog1 = builder1.create();
                dialog1.show();
                break;
            case R.id.about1:
                Log.d("Toolbar","Option about selected");
                Toast.makeText(this, "Version 1.0, by Weikai Li", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }



}
