package com.weikaili.androidlabs;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.data;
import static android.R.attr.defaultValue;

/**
 * Created by Li Weikai on 2017-03-20.
 */

public class MessageFragment extends Fragment {


    Context parent;
    Long id;
    int intid;
    String message;
    static final int resultCode = 1;
    ChatWindow chat;

    public MessageFragment(ChatWindow chat)
    {
        this.chat =  chat;
    }

       // chat.onActivityResult(1,1, data);

    //no matter how you got here, the data is in the getArguments

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        Bundle bun = getArguments();
        id = bun.getLong("ID");
       // intid = bungetLong.getInt("I");
        message = bun.getString("Message");
        Log.i("ID",""+id);
        Log.i("Message",""+message);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        parent = context;
    }
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            //return super.onCreateView(inflater, container, savedInstanceState);
            View gui = inflater.inflate(R.layout.fragment_layout, null);

            TextView tvMessage = (TextView)gui.findViewById(R.id.tvMessage);
            tvMessage.setText(message);
            TextView tv = (TextView)gui.findViewById(R.id.tvId);
            tv.setText("ID = "+id);
            Button btn = (Button)gui.findViewById(R.id.buttonDel);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (chat== null) {
                        Intent data = new Intent();
                        data.putExtra("delete", id);
                        Log.i("delete", "" + id);
                        getActivity().setResult(resultCode, data);
                        getActivity().finish();
                    }
                    else{/*
                        Intent data = new Intent();
                        data.putExtra("delete", intid);
                        chat.setResult(resultCode,data);
                        chat.onActivityResult(1,1,data);
                        chat.finish();*/
                        chat.deleteId(id);
                        getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
                    }
                }
            });
            return gui;

    }
}
