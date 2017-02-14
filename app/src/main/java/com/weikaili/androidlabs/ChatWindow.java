package com.weikaili.androidlabs;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.id.list;

public class ChatWindow extends AppCompatActivity {
    protected ListView lv;
    protected EditText et;
    protected Button btn;
    public ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        lv = (ListView)findViewById(R.id.chatListView);
        et = (EditText)findViewById(R.id.chatText);
        btn = (Button)findViewById(R.id.chatSend);
        final ChatAdapter messageAdapter = new ChatAdapter(this);
        lv.setAdapter(messageAdapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.add(et.getText().toString());
                messageAdapter.notifyDataSetChanged();
                et.setText("");
            }
        });

    }
    private class ChatAdapter extends ArrayAdapter<String> {
        private ChatAdapter(Context cont){
            super(cont, 0);
        }
        public int getCount(){
            return arrayList.size();
        }
        public String getItem(int position){
            return arrayList.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            TextView message;
            if(position%2==0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
                 message = (TextView) result.findViewById(R.id.textView3);
            }
                else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
                message = (TextView) result.findViewById(R.id.message_text);
            }
            message.setText(getItem(position));
            return result;
            }
        }
    }

