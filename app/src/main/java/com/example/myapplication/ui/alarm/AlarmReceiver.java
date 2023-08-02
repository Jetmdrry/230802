package com.example.myapplication.ui.alarm;


import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.myapplication.MySqliteOpenHelper;
import com.example.myapplication.R;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        /*Uri myUri = Uri.fromFile(new File("app/src/main/res/raw/alarm_music.mp3")); // initialize Uri here
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(context, myUri);
            mediaPlayer.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mediaPlayer.start();*/
        //在raw文件夹添加音乐alarm_music
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.alarm_music);
        mediaPlayer.start();
        int id = intent.getIntExtra("id",-1);
        SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        if (db.isOpen()) {
                String sql2 = "UPDATE alarms SET switch_on = false WHERE _id = ?;";
                db.execSQL(sql2, new Object[]{id});
            db.close();
        }
        Toast.makeText(context, "闹钟响了 id:"+id, Toast.LENGTH_LONG).show();
        Log.d("TAG", "onReceive: ");
        Intent i = new Intent(context, OnRingActivity.class);
        //这个很重要，如果没有这一句，那就会报错，这一句是因为我们是在一个Receiver里面启动一个activity的，但activity的启动，都是放到一个栈里面的，但Receiver里面没有那个栈，所以我们要在这里启动一个activity，那就必须要指定这行代码
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        /*WindowManager wManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        //mParams.format = PixelFormat.TRANSLUCENT;// 支持透明
        //mParams.format = PixelFormat.RGBA_8888;l
//        mParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;// 焦点

        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                // 加上这句话悬浮窗不拦截事件
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;*/



        //在raw文件夹添加音乐

    }
}