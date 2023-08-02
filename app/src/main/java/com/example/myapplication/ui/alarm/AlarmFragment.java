package com.example.myapplication.ui.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MySqliteOpenHelper;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAlarmBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmFragment extends Fragment {

    private FragmentAlarmBinding binding;

    private List<AlarmBean> data = new ArrayList<>();
    AlarmListAdapter alarmListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AlarmViewModel alarmViewModel =
                new ViewModelProvider(this).get(AlarmViewModel.class);

        binding = FragmentAlarmBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        RecyclerView recyclerView = root.findViewById(R.id.rv_alarm);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(this.getContext());

        alarmListAdapter = new AlarmListAdapter(data, this.getContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(this.requireContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(alarmListAdapter);
//添加和修改
        binding.btnAddAlarm.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), AlarmEditActivity.class).putExtra("id", -1);
            startActivity(intent);
        });
        alarmListAdapter.setOnRecyclerItemClickListener(position -> {
            Intent intent = new Intent(AlarmFragment.this.getContext(), AlarmEditActivity.class)
                    .putExtra("id", data.get(position).get_id())
                    .putExtra("position", position);
            startActivity(intent);
        });
        alarmListAdapter.setOnRecyclerItemCheckedChangeListener(new AlarmListAdapter.OnRecyclerItemCheckedChangeListener() {
            @Override
            public void OnRecyclerItemCheckedChange(int position, boolean b) {
                SQLiteDatabase db2 = helper.getWritableDatabase();
                int id = data.get(position).get_id();
                if (db2.isOpen()) {
                    String sql = "UPDATE alarms SET switch_on = ? WHERE _id = ?;";
                    db2.execSQL(sql, new Object[]{b, id});
                    AlarmManager alarmMgr = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getContext(), AlarmReceiver.class);
                    intent.putExtra("id",id);
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext(), id, intent, PendingIntent.FLAG_IMMUTABLE);
                    if(b){
                        Cursor cursor = db2.rawQuery("SELECT * FROM alarms WHERE _id = ?", new String[]{"" + id});
                        cursor.moveToPosition(0);
                        Calendar startTime = Calendar.getInstance();
                        startTime.set(Calendar.HOUR_OF_DAY, cursor.getInt(1));
                        startTime.set(Calendar.MINUTE, cursor.getInt(2));
                        //此行代码是解决startTime小于当前时间时，闹钟会直接响的问题。但是为了方便调试，暂时注释掉了
                        //if(startTime.getTimeInMillis()<Calendar.getInstance().getTimeInMillis()){startTime.add(Calendar.DATE,1);}
                        Log.d("TAG", "onClick: " + startTime.getTime());
                        alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(), alarmIntent);
                        cursor.close();
                    }else {
                        alarmMgr.cancel(alarmIntent);
                        Toast.makeText(getContext(),"闹钟，关闭！",Toast.LENGTH_SHORT).show();
                    }
                    db2.close();
                }

            }
        });


        //final TextView textView = binding.textDashboard;
        //alarmViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        data.clear();
        SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(this.getContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from alarms order by hour, minute", null);
            while (cursor.moveToNext()) {
                AlarmBean bean = new AlarmBean();
                bean.set_id(cursor.getInt(0));
                bean.setHour(cursor.getInt(1));
                bean.setMinute(cursor.getInt(2));
                bean.setSwitch_on(cursor.getLong(3) > 0);
                data.add(bean);
            }
            cursor.close();
            db.close();
        }
        alarmListAdapter.updateData(data);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}