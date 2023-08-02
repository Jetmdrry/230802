package com.example.myapplication.ui.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MySqliteOpenHelper;
import com.example.myapplication.R;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemoEditActivity extends AppCompatActivity {

    EditText editText;
    EditText editTextTitle;
    EditText editDate;
    EditText editTime;
    Button btnCancel;
    Button btnSave;
    Calendar time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);
        editText = this.findViewById(R.id.editText);
        editTextTitle = this.findViewById(R.id.et_memotitle);
        editDate = this.findViewById(R.id.et_memodate);
        editTime = this.findViewById(R.id.et_memotime);
        btnCancel = this.findViewById(R.id.btn_cancel);
        btnSave = this.findViewById(R.id.btn_save);
        time = Calendar.getInstance();
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        Log.d("edit:", "onCreate: " + id);
        if (id != -1) {
            SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(MemoEditActivity.this);
            SQLiteDatabase db = helper.getWritableDatabase();
            if (db.isOpen()) {
                Cursor cursor = db.rawQuery("SELECT * FROM memos WHERE _id = ?", new String[]{"" + id});
                cursor.moveToPosition(0);
                editTextTitle.setText(cursor.getString(1));
                editText.setText(cursor.getString(2));
                time.setTimeInMillis(cursor.getLong(3));
                cursor.close();
                db.close();
            }
        }
        editDate.setText(String.format("%04d-%02d-%02d",time.get(Calendar.YEAR),time.get(Calendar.MONTH),time.get(Calendar.DATE)));
        editTime.setText(String.format("%02d:%02d",time.get(Calendar.HOUR_OF_DAY),time.get(Calendar.MINUTE)));
        editDate.setOnFocusChangeListener((view, b) -> {if(!b){CheckAndSetDate();}});
        editTime.setOnFocusChangeListener((view, b) -> {if(!b){CheckAndSetTime();}});
        btnCancel.setOnClickListener(view -> onBackPressed());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存
                if(CheckAndSetDate()&&CheckAndSetTime()){
                    SQLiteOpenHelper helper = MySqliteOpenHelper.getmInstance(MemoEditActivity.this);
                    SQLiteDatabase db = helper.getWritableDatabase();

                    if (db.isOpen()) {
                        if (id == -1) {
                            String sql1 = "insert into memos(title,text,check_on,time) values(?,?,false,?)";
                            db.execSQL(sql1, new Object[]{editTextTitle.getText().toString(),editText.getText().toString(),time.getTimeInMillis()});
                        } else {
                            String sql2 = "UPDATE memos SET title = ?, text = ?, time = ? WHERE _id = ?;";
                            db.execSQL(sql2, new Object[]{editTextTitle.getText().toString(),editText.getText().toString(),time.getTimeInMillis(), id});
                        }
                        onBackPressed();
                        db.close();
                    }
                }

            }
        });
    }
    private boolean CheckAndSetDate(){
        String date = editDate.getText().toString();
        Pattern p = Pattern.compile("((20[0-9]{2})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|(((2[0-9])(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)" );
        Matcher m = p.matcher((date));
        if (!m.matches()){
            editDate.setText(String.format("%04d-%02d-%02d",time.get(Calendar.YEAR),time.get(Calendar.MONTH),time.get(Calendar.DATE)));
            Toast.makeText(this,"请输入格式为YYYY-MM-DD的日期，范围在2000-2999年之间",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            String[] strings=editDate.getText().toString().split("-");
            time.set(Integer.parseInt(strings[0]),Integer.parseInt(strings[1]),Integer.parseInt(strings[2]));
            return true;
        }
    }
    private boolean CheckAndSetTime(){
        String t = editTime.getText().toString();
        Pattern p = Pattern.compile("([01][0-9]|2[0-3]):([0-5][0-9])" );
        Matcher m = p.matcher((t));
        if (!m.matches()){
            editTime.setText(String.format("%02d:%02d",time.get(Calendar.HOUR_OF_DAY),time.get(Calendar.MINUTE)));
            Toast.makeText(this,"请输入格式为HH:MM的正确时间",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            String[] strings=editTime.getText().toString().split(":");
            time.set(Calendar.HOUR_OF_DAY,Integer.parseInt(strings[0]));
            time.set(Calendar.MINUTE,Integer.parseInt(strings[1]));
            return true;
        }
    }
}