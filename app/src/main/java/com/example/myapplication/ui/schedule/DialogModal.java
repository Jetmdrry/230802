package com.example.myapplication.ui.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class DialogModal extends Activity {
    Button close_activity;
    Button save_activity;
    Spinner selected_starttime;
    Spinner selected_endtime;
    Spinner selected_startweek;
    Spinner selected_endweek;
    Spinner selected_label;
    Spinner selected_day;
    EditText subject;
    EditText selected_teacher;

    //数据库名，表名
    public final String DB_NAME = "classes_db.db";
    public final String TABLE_NAME = "classes_db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set);

        //当点击dialog之外完成此activity
        setFinishOnTouchOutside (true);

        //关闭按钮操作
        close_activity=(Button) findViewById(R.id.close_activity);
        close_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogModal.this.finish();
            }
        });

        //保存按钮操作
        save_activity = findViewById(R.id.save_activity);
        save_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                selected_day = findViewById(R.id.selected_day);
                String day = selected_day.getSelectedItem().toString();
                if (day.equals("--请选择--")) {
                    save_activity.setError("");
                    return;
                }

                selected_starttime = findViewById(R.id.selected_starttime);
                String starttime = selected_starttime.getSelectedItem().toString();
                if (starttime.equals("--请选择--")) {
                    save_activity.setError("");
                    return;
                }

                selected_endtime = findViewById(R.id.selected_endtime);
                String endtime = selected_endtime.getSelectedItem().toString();
                if (endtime.equals("--请选择--")|| utils.gettime(endtime) < utils.gettime(starttime)) {
                    save_activity.setError("");
                    return;
                }

                selected_startweek = findViewById(R.id.selected_startweek);
                String startweek = selected_startweek.getSelectedItem().toString();
                if (startweek.equals("--请选择--")) {
                    save_activity.setError("");
                    return;
                }

                selected_endweek = findViewById(R.id.selected_endweek);
                String endweek = selected_endweek.getSelectedItem().toString();
                if (endweek.equals("--请选择--")|| utils.getweek(endweek) < utils.getweek(startweek)) {
                    save_activity.setError("");
                    return;
                }

                selected_label = findViewById(R.id.selected_label);
                String label = selected_label.getSelectedItem().toString();
                if (label.equals("--请选择--")) {
                    save_activity.setError("");
                    return;
                }


                subject = findViewById(R.id.subject);
                String text = subject.getText().toString();
                if ("".equals(text)) {
                    save_activity.setError("");
                    return;
                }

                selected_teacher = findViewById(R.id.teacher);
                String teacher = selected_teacher.getText().toString();
               /* if ("".equals(teacher)) {
                    save_activity.setError("");
                    return;
                }*/

                //创建一个数据库对象
                DBHelper dbHelper = new DBHelper(DialogModal.this, DB_NAME, null, 1);

                //把数据存在contentValues中
                ContentValues contentValues = new ContentValues();
                //combineId()方法目的是通过星期几和第几节课开始算出对应该课程id
                contentValues.put("c_id", combineId(day, starttime));
                contentValues.put("c_name", text);
                contentValues.put("c_starttime", starttime);
                contentValues.put("c_endtime", endtime);
                contentValues.put("c_startweek", startweek);
                contentValues.put("c_endweek", endweek);
                contentValues.put("c_label", label);
                contentValues.put("c_day", day);
                contentValues.put("c_teacher", teacher);

                //更新数据库记录
                update(dbHelper, contentValues);

                //清空栈内所有activity
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);

                //启动MainActivity
                intent.setClass(getBaseContext(), ScheduleFragment.class);
                startActivity(intent);
            }
        });
    }

    public String combineId(String day, String starttime) {

        //星期几转换成int类型
        int day1 = utils.getDay(day);

        //如1-2节课只取1
        int time1 = utils.gettime(starttime);

        return String.valueOf((day1 - 1) * 13 + ((time1 - 1) + 1));

    }

    public void update(DBHelper dbHelper, ContentValues contentValues){

        String []a = {contentValues.get("c_id").toString()};

        // 通过DBHelper类获取一个读写的SQLiteDatabase对象
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        // 修改数据
        // 参数1：tablename
        // 参数2：修改的值
        // 参数3：修改的条件（SQL where语句）
        // 参数4：表示whereClause语句中的表达式的占位符参数列表，这些字符串会替换where条件中?
        db.update(TABLE_NAME,contentValues,"c_id=?", a);

        // 释放连接
        db.close();
    }

}
