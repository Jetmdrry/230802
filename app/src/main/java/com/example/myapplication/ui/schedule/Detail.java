package com.example.myapplication.ui.schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class Detail extends Activity {
    Button close;
    Button delete;
    TextView selected_starttime;
    TextView selected_endtime;
    TextView selected_startweek;
    TextView selected_endweek;
    TextView selected_label;
    TextView selected_day;
    TextView sub;
    TextView selected_teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_press);

        Intent intent = getIntent();

        String a1 = intent. getIntExtra("id",0)+"";
        String a2 = intent. getStringExtra("startweek");

        close=(Button) findViewById(R.id.close_activity);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Detail.this.finish();
            }
        });



        delete = (Button) findViewById(R.id.delete_activity);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHelper dbHelper = new DBHelper(Detail.this, "classes_db.db", null, 1);
                DBHelper.delete(dbHelper,a1,a2);

                //Detail.this.finish();
                //清空栈内所有activity
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
                //启动MainActivity
                intent.setClass(getBaseContext(),ScheduleFragment.class);
                startActivity(intent);

            }
        });

        sub = findViewById(R.id.sub);
        sub.setText(intent.getStringExtra("sub"));

        selected_starttime = findViewById(R.id.starttime);
        selected_starttime.setText(intent.getStringExtra("starttime"));

        selected_endtime = findViewById(R.id.endtime);
        selected_endtime.setText(intent.getStringExtra("endtime"));

        selected_startweek = findViewById(R.id.startweek);
        selected_startweek.setText(intent.getStringExtra("startweek"));

        selected_endweek = findViewById(R.id.endweek);
        selected_endweek.setText(intent.getStringExtra("endweek"));

        selected_label = findViewById(R.id.label);
        selected_label.setText(intent.getStringExtra("label"));

        selected_day = findViewById(R.id.day);
        selected_day.setText(intent.getStringExtra("day"));

        selected_teacher = findViewById(R.id.teacher);
        selected_teacher.setText(intent.getStringExtra("teacher"));

        setFinishOnTouchOutside(true);


    }

}
