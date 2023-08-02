package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.ui.alarm.AlarmBean;
import com.example.myapplication.ui.alarm.AlarmReceiver;

import java.util.Calendar;

/*
此Receiver用于开机重启时重新启用闹钟
*/
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
            SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(context);
            SQLiteDatabase db = helper.getWritableDatabase();
            if (db.isOpen()) {
                Cursor cursor = db.rawQuery("select * from alarms order by hour, minute", null);
                while (cursor.moveToNext()) {
                    if (cursor.getLong(3) > 0) {
                        int id = cursor.getInt(0);
                        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                        Intent i = new Intent(context, AlarmReceiver.class);
                        i.putExtra("id", id);
                        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, id, i, PendingIntent.FLAG_IMMUTABLE);
                        Calendar startTime = Calendar.getInstance();
                        startTime.set(Calendar.HOUR_OF_DAY, cursor.getInt(1));
                        startTime.set(Calendar.MINUTE, cursor.getInt(2));
                        startTime.set(Calendar.SECOND,0);
                        if (startTime.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
                            startTime.add(Calendar.DATE, 1);
                        }
                        Log.d("TAG", "onRestartAlarm: " + startTime.getTime());
                        alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                    }
                }
                cursor.close();
                db.close();
            }
        }
    }
}