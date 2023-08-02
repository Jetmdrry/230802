package com.example.myapplication.ui.alarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.MySqliteOpenHelper;
import com.example.myapplication.R;
import com.example.myapplication.ui.notifications.MemoEditActivity;
import com.example.myapplication.ui.notifications.NotificationsFragment;

import java.time.LocalDateTime;
import java.util.Calendar;

public class AlarmEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_edit);


        TimePicker timePicker = findViewById(R.id.timePicker);
        Button setAlarmBtn =findViewById(R.id.btn_set_alarm);
        Button cancelBtn = findViewById(R.id.btn_cancel);
        Button deleteBtn = findViewById(R.id.btn_delete);

        Intent i = getIntent();
        int id = i.getIntExtra("id", -1);
        int position = i.getIntExtra("position",0);
        if (id != -1) {
            SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            if (db.isOpen()) {
                Cursor cursor = db.rawQuery("SELECT * FROM alarms WHERE _id = ?", new String[]{"" + id});
                cursor.moveToPosition(0);
                timePicker.setHour(cursor.getInt(1));
                timePicker.setMinute(cursor.getInt(2));
                cursor.close();
                db.close();
            }
        }



        setAlarmBtn.setOnClickListener(view -> {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();


            //检查权限
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M &&
                    Settings.canDrawOverlays(AlarmEditActivity.this) == false) {
                startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + AlarmEditActivity.this.getPackageName())));
            }

            Toast.makeText(AlarmEditActivity.this, "闹钟设置成功！", Toast.LENGTH_SHORT).show();
                    SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(this);
                    SQLiteDatabase db = helper.getWritableDatabase();

                    if (db.isOpen()) {
                        if (id == -1) {
                            String sql1 = "insert into alarms(hour,minute,switch_on) values(?,?,true)";
                            db.execSQL(sql1, new Object[]{hour,minute});

                        } else {
                            String sql2 = "UPDATE alarms SET hour = ?, minute = ? WHERE _id = ?;";
                            db.execSQL(sql2, new Object[]{hour,minute, id});
                        }
                        db.close();
                    }
            Intent iBack = new Intent();
            iBack.putExtra("id", id)
                    .putExtra("position",position)
                    .putExtra("hour",hour)
                    .putExtra("minute",minute);
            setResult(1, iBack);
            finish();
        });
        cancelBtn.setOnClickListener(view -> {
            onBackPressed();
        });
        deleteBtn.setOnClickListener(view -> {
            SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            if (db.isOpen()) {
                String sql = "delete from alarms where _id =?";
                db.execSQL(sql, new Object[]{id});
                db.close();
            }
            Intent iBack = new Intent();
            setResult(-1, iBack);
            finish();
        });
    }


}