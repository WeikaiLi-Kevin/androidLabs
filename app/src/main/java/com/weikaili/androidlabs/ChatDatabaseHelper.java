package com.weikaili.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Li Weikai on 2017-02-26.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "MyDatabase";
    final static int VERSION_NUM = 2;
    final static String  KEY_MESSAGE = "Message";
    final static String KEY_ID = "_id";
    public ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME,null, VERSION_NUM);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DATABASE_NAME + " ("+KEY_ID + " INTEGER PRIMARY KEY, "+ KEY_MESSAGE+" text);" );

        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);

    }
}
