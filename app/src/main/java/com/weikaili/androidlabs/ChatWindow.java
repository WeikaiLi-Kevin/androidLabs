package com.weikaili.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.id.list;
import static android.R.id.message;

public class ChatWindow extends AppCompatActivity {
    protected ListView lv;
    protected EditText et;
    protected Button btn;
    public ArrayList<String> arrayList = new ArrayList<>();
    public ArrayList<Long> arrayId = new ArrayList<>();

    protected  SQLiteDatabase db;
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private boolean isTablet;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public ChatAdapter messageAdapter;// = new ChatAdapter(this);
    Cursor results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_window);
        lv = (ListView)findViewById(R.id.chatListView);
        et = (EditText)findViewById(R.id.chatText);
        btn = (Button)findViewById(R.id.chatSend);
        messageAdapter = new ChatAdapter(this);
         lv.setAdapter(messageAdapter);
        final ContentValues newValues = new ContentValues();

        isTablet = (findViewById(R.id.fragmentHolder) != null);
        Log.i("isTablet",""+isTablet);
        ChatDatabaseHelper chatdbHelper;
        chatdbHelper = new ChatDatabaseHelper(this);
        db = chatdbHelper.getWritableDatabase();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.add(et.getText().toString());
                messageAdapter.notifyDataSetChanged();
                newValues.put(ChatDatabaseHelper.KEY_MESSAGE,et.getText().toString() );
                arrayId.add( db.insert(ChatDatabaseHelper.DATABASE_NAME, "", newValues));
                et.setText("");
            }
        });
       results = db.query(false,ChatDatabaseHelper.DATABASE_NAME,new String[]{ChatDatabaseHelper.KEY_ID,ChatDatabaseHelper.KEY_MESSAGE},null,
                null,null,null,null,null
        );
        //int rows = results.getCount();
        int messageIndex = results.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);
        results.moveToFirst();
        while(!results.isAfterLast()) {
           Log.i(ACTIVITY_NAME,"SQL MESSAGE:"+results.getString(messageIndex));
            Log.i(ACTIVITY_NAME,"Cursor's column count =" + results.getColumnCount()) ;
              arrayList.add(results.getString(messageIndex));
            arrayId.add(results.getLong(results.getColumnIndex(ChatDatabaseHelper.KEY_ID)));
            results.moveToNext();
        }
        for(int num = 0 ;num<results.getColumnCount();num++)
        Log.i(ACTIVITY_NAME,results.getColumnName( num));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("ListView", "onItemClick: " + i + " " + l);


                Bundle bun = new Bundle();
                bun.putLong("ID", l );//l is the database ID of selected item
                bun.putString("Message",arrayList.get(i));

                //step 2, if a tablet, insert fragment into FrameLayout, pass data
                if(isTablet) {
                     MessageFragment frag= new MessageFragment(ChatWindow.this);


                    frag.setArguments(bun);

                    getFragmentManager().beginTransaction().replace(R.id.fragmentHolder, frag).commit();
                }
                //step 3 if a phone, transition to empty Activity that has FrameLayout
                else //isPhone
                {
                    Intent intnt = new Intent(ChatWindow.this, MessageDetails.class);
                    intnt.putExtras(bun);//("ID" , l); //pass the Database ID to next activity
                   // intnt.putExtra("Message",arrayList.get(i));
                  //  intnt.putExtra("I",i);
                    startActivityForResult(intnt,REQUEST_IMAGE_CAPTURE ); //go to view fragment details
                }
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

        public long getItemId(int pos)
        {
            return arrayId.get(pos);
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
    protected void onDestroy(){

        super.onDestroy();
        db.close();
    }
    protected  void  onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode==1) {
            Long result = data.getExtras().getLong("delete");//得到新Activity 关闭后返回的数据
            Log.i("result", "" + result);
            // final ChatAdapter messageAdapter = new ChatAdapter(this);
            //lv.setAdapter(messageAdapter);
            deleteId(result);
        }
    }
    public void deleteId(long id)
    {

        db.delete(ChatDatabaseHelper.DATABASE_NAME, "_id=?", new String[] { Long.toString(id)});
        arrayId.clear();
        arrayList.clear();//;


        results = db.query(false,ChatDatabaseHelper.DATABASE_NAME,new String[]{ChatDatabaseHelper.KEY_ID,ChatDatabaseHelper.KEY_MESSAGE},null,
                null,null,null,null,null
        );
        //int rows = results.getCount();
        int messageIndex = results.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);
        results.moveToFirst();
        // arrayId.clear();
        //int i = 0;
        while(!results.isAfterLast()) {
            Log.i(ACTIVITY_NAME,"SQL MESSAGE:"+results.getString(messageIndex));
            Log.i(ACTIVITY_NAME,"Cursor's column count =" + results.getColumnCount()) ;
            arrayList.add(results.getString(messageIndex));
            //arrayId.add();
            arrayId.add(results.getLong(results.getColumnIndex(ChatDatabaseHelper.KEY_ID)));
            results.moveToNext();
        }


        messageAdapter.notifyDataSetChanged();

    }
    }

