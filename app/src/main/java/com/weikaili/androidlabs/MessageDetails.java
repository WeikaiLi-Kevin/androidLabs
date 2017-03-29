package com.weikaili.androidlabs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Li Weikai on 2017-03-20.
 */

public class MessageDetails extends Activity {
    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_message_details);


        //Step 3, create  fragment onCreation, pass data from Intent Extras to FragmentTransction
        MessageFragment frag = new MessageFragment(null);
        Bundle bun =        getIntent().getExtras();
        //Log.i("GetInformation",""+getIntent().getExtras());
        frag.setArguments( bun );
        getFragmentManager().beginTransaction().add(R.id.fragmentHolder, frag).commit();



    }
}
