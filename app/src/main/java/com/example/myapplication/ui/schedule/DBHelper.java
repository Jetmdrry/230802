package com.example.myapplication.ui.schedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    public DBHelper(@Nullable Context context,
                    @Nullable String name,
                    @Nullable SQLiteDatabase.CursorFactory factory,
                    int version) {

        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table classes_db" +
                "(c_id Integer not null primary key autoincrement," +
                " c_name varchar(50) not null," +
                " c_starttime varchar(50) not null," +
                " c_endtime varchar(50) not null," +
                " c_day varchar(50) not null," +
                " c_label varchar(50) not null," +
                " c_teacher varchar(50) not null," +
                " c_startweek varchar(50) not null," +
                " c_endweek varchar(50) not null)" );

        // 创建ContentValue设置参数
        ContentValues contentValues = new ContentValues();

        //课程名字
        contentValues.put("c_name", "0");
        //第几节课开始
        contentValues.put("c_starttime", "0");
        //第几节课结束
        contentValues.put("c_endtime", "0");
        //星期几
        contentValues.put("c_day", "0");
        //任课教师-->备注
        contentValues.put("c_teacher", "0");
        //开始周
        contentValues.put("c_startweek", "0");
        //结束周
        contentValues.put("c_endweek", "0");
        //标签
        contentValues.put("c_label", "0");


        //插入id 1-36 个课程数据，以便添加课程
        for (int i = 1; i < 100; i++) {
            contentValues.put("c_id", i);
            sqLiteDatabase.insert("classes_db", "", contentValues);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static void delete(DBHelper dbHelper,String id,String startweek)
    {

        // 通过DBHelper类获取一个读写的SQLiteDatabase对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("c_day","0");
        contentValues.put("c_name","0");
        contentValues.put("c_id",id);

        Cursor cursor = db.query("classes_db", null, null, null, null, null, null);

        // 将游标移到开头
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) { // 游标只要不是在最后一行之后，就一直循环

            if (cursor.getString(Math.abs(cursor.getColumnIndex("c_id"))).equals(id)&&cursor.getString(Math.abs(cursor.getColumnIndex("c_startweek"))).equals(startweek)){

                String []a = {contentValues.get("c_id").toString()};
                db.update("classes_db",contentValues,"c_id=?",a);
            }

            // 将游标移到下一行
            cursor.moveToNext();

        }

        db.close();
    }

    public static int getweek(DBHelper dbHelper)
    {

        // 通过DBHelper类获取一个读写的SQLiteDatabase对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();
       int aaa = 1;


        Cursor cursor = db.query("classes_db", null, null, null, null, null, null);

        // 将游标移到开头
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) { // 游标只要不是在最后一行之后，就一直循环

            if (cursor.getString(Math.abs(cursor.getColumnIndex("c_name"))).equals("0")&&cursor.getString(Math.abs(cursor.getColumnIndex("c_label"))).equals("123")){
               aaa=Integer.parseInt(cursor.getString(Math.abs(cursor.getColumnIndex("c_id"))));

            }

            // 将游标移到下一行
            cursor.moveToNext();

        }

        db.close();
       return aaa;
    }
}
