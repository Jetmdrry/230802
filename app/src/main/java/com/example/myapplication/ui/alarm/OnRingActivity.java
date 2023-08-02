package com.example.myapplication.ui.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.notifications.NotificationsFragment;
import com.example.myapplication.ui.schedule.ScheduleFragment;

public class OnRingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PowerManager mPowerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");
        mWakeLock.acquire(60*1000L);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        /*WindowManager.LayoutParams lp=getWindow().getAttributes();
        lp.alpha=0.2f;
        getWindow().setAttributes(lp);*/
        setContentView(R.layout.activity_on_ring);
        TextView textView = findViewById(R.id.tv);

        textView.setOnClickListener(view -> {
            Intent intent=new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

    }
}