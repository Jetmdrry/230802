package com.example.myapplication.ui.notifications;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MySqliteOpenHelper;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentNotificationsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private static final String TAG = "NotificationsFragment";

    MyAdapter myAdapter;
    private List<Bean> data = new ArrayList<>();
    FloatingActionButton floatingActionButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        floatingActionButton = root.findViewById(R.id.floatingActionButton);


        RecyclerView recyclerView = root.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        myAdapter = new MyAdapter(data, this.getContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(this.requireContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(myAdapter);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationsFragment.this.getContext(), MemoEditActivity.class).putExtra("id", -1);
                startActivity(intent);
            }
        });

        myAdapter.setOnRecyclerItemClickListener(new MyAdapter.OnRecyclerItemClickListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void OnRecyclerItemClick(int position, int viewId) {
                if (viewId == R.id.btn_clear) {
                    SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(NotificationsFragment.this.getContext());
                    SQLiteDatabase db = helper.getWritableDatabase();
                    if (db.isOpen()) {
                        String sql = "delete from memos where _id =?";
                        db.execSQL(sql, new Object[]{data.get(position).get_id()});
                        db.close();
                    }
                    myAdapter.removeData(position);
                    Log.d(TAG, "OnRecyclerItemClick: "+data.toString());
                } else if (viewId == R.id.btn_check) {
                    Bean tmpBean = data.get(position);
                    SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(NotificationsFragment.this.getContext());
                    SQLiteDatabase db = helper.getWritableDatabase();
                    tmpBean.setCheckOn(!tmpBean.isCheckOn());
                    data.set(position,tmpBean);
                    if (db.isOpen()) {
                        String sql = "UPDATE memos SET check_on = ? WHERE _id = ?;";
                        db.execSQL(sql, new Object[]{tmpBean.isCheckOn(),data.get(position).get_id()});
                        db.close();
                    }
                    myAdapter.checkData(position,tmpBean.isCheckOn());
                } else {
                    Intent intent = new Intent(NotificationsFragment.this.getContext(), MemoEditActivity.class).putExtra("id", data.get(position).get_id());
                    startActivity(intent);
                }

            }
        });

        //final TextView textView = binding.textNotifications;
        //notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        data.clear();
        SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(this.getContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from memos order by time desc", null);
            while (cursor.moveToNext()) {
                Bean bean = new Bean();
                bean.set_id(cursor.getInt(0));
                bean.setName(Objects.equals(cursor.getString(1), "") ?cursor.getString(2):cursor.getString(1));
                bean.setTime(cursor.getLong(3));
                bean.setCheckOn(cursor.getInt(4)>0);
                data.add(bean);
            }
            cursor.close();
            db.close();
        }
        myAdapter.updateData(data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}