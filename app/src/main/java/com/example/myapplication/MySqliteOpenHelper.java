package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

    private static SQLiteOpenHelper mInstance;
    public static synchronized SQLiteOpenHelper getmInstance(Context context){
        if(mInstance == null){
            mInstance = new MySqliteOpenHelper(context,"app.db",null,1);
        }
        return mInstance;
    }
    public MySqliteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table memos(_id integer primary key autoincrement, title text, text text, time text, check_on boolean)";
        db.execSQL(sql);
        sql = "create table schedules(_id integer primary key autoincrement, week integer, weekday integer, class_start integer,class_end integer, name text)";
        db.execSQL(sql);
        sql = "create table alarms(_id integer primary key autoincrement, hour integer, minute integer, switch_on boolean, notification text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
