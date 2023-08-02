package com.example.myapplication.ui.schedule;

import static android.provider.SyncStateContract.Helpers.update;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSchuduleBinding;
import com.example.myapplication.ui.notifications.MemoEditActivity;
import com.example.myapplication.ui.notifications.NotificationsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment {

    private FragmentSchuduleBinding binding;
//>
    GridLayout gridLayout;

    /////////

    /////////
    //<
    public final String DB_NAME = "classes_db.db";
    public final String TABLE_NAME = "classes_db";
    public int weekc =1;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        ScheduleViewModel scheduleViewModel =
                new ViewModelProvider(this).get(ScheduleViewModel.class);

        binding = FragmentSchuduleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



///////////////////



        DBHelper dbHelper2 = new DBHelper(getContext(), DB_NAME, null, 1);


        //把数据存在contentValues中
     /*   ContentValues contentValues2 = new ContentValues();
        //combineId()方法目的是通过星期几和第几节课开始算出对应该课程id
        contentValues2.put("c_id", String.valueOf(weekc));
        contentValues2.put("c_name", "0");
        contentValues2.put("c_label", "123");
        contentValues2.put("c_day", "0");
        contentValues2.put("c_teacher", "0");
        contentValues2.put("c_starttime", "0");
        contentValues2.put("c_endtime", "0");
        contentValues2.put("c_startweek", "0");
        contentValues2.put("c_endweek", "0");
        //更新数据库记录
        SQLiteDatabase db2 = dbHelper2.getWritableDatabase();
        String []a = {"123"};
        db2.update("classes_db",contentValues2,"c_label=?",a);
        weekc = DBHelper.getweek(dbHelper2);

*/
        /////////////////////////////
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_schudule);


        //拿到数据库对象，为了读取数据
        DBHelper dbHelper = new DBHelper(getContext(), DB_NAME, null, 1);

        //动态渲染课程框
        framework();

        //将数据库课程渲染到课程框
        applyDraw(dbHelper);

        Button btnw = (Button)root.findViewById(R.id.addclass);
        btnw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getContext(), DialogModal.class);
                startActivity(intent);
            }
        });

        TextView textView = root.findViewById(R.id.week);
        Button btn1 = (Button)root.findViewById(R.id.weekup);



        textView.setText("第"+weekc+"周");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weekc<20) {
                    weekc ++;

                    DBHelper dbHelper = new DBHelper(getContext(), DB_NAME, null, 1);
                    fuyuan();
                    applyDraw(dbHelper);
                }
                textView.setText("第"+weekc+"周");
//////////
               /* //把数据存在contentValues中
                ContentValues contentValues2 = new ContentValues();
                //combineId()方法目的是通过星期几和第几节课开始算出对应该课程id
                contentValues2.put("c_id",String.valueOf(weekc) );
                contentValues2.put("c_name", "0");
                contentValues2.put("c_label", "123");
                contentValues2.put("c_day", "0");
                contentValues2.put("c_teacher", "0");
                //更新数据库记录
                SQLiteDatabase db2 = dbHelper2.getWritableDatabase();
                String []a = {"123"};
                db2.update("classes_db",contentValues2,"c_label=?",a);*/

  ///////
            } } );

        Button btn2 = (Button)root.findViewById(R.id.weekdown);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weekc>1) {
                    weekc--;

                    DBHelper dbHelper = new DBHelper(getContext(), DB_NAME, null, 1);
                    fuyuan();
                    applyDraw(dbHelper);
                }
                textView.setText("第"+weekc+"周");
                ////////////
                /*//把数据存在contentValues中
                ContentValues contentValues3 = new ContentValues();
                //combineId()方法目的是通过星期几和第几节课开始算出对应该课程id
                contentValues3.put("c_id", String.valueOf(weekc));
                contentValues3.put("c_name", "0");
                contentValues3.put("c_label", "123");
                contentValues3.put("c_day", "0");
                contentValues3.put("c_teacher", "0");
                //更新数据库记录
                SQLiteDatabase db2 = dbHelper2.getWritableDatabase();
                String []a = {"123"};
                db2.update("classes_db",contentValues3,"c_label=?",a);*/

                /////////

            } } );

       //getActionBar().hide();



        /////////////////////////////
        return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

/////////////////////////////////
public GridLayout LayoutColumn(int i) {
    View root= binding.getRoot();
    GridLayout gridLayout = root.findViewById(R.id.d1);

    switch (i) {
        case 1: {
            gridLayout = root.findViewById(R.id.d1);
            break;
        }
        case 2: {
            gridLayout = root.findViewById(R.id.d2);
            break;
        }
        case 3: {
            gridLayout = root.findViewById(R.id.d3);
            break;
        }
        case 4: {
            gridLayout = root.findViewById(R.id.d4);
            break;
        }
        case 5: {
            gridLayout = root.findViewById(R.id.d5);
            break;
        }
        case 6: {
            gridLayout = root.findViewById(R.id.d6);
            break;
        }
        case 7: {
            gridLayout =root. findViewById(R.id.d7);
            break;
        }
    }
    return gridLayout;
}

    public void framework() {

        GridLayout gridLayout;
        int id = 1;

        for (int i = 1; i < 8; i++) {

            gridLayout = LayoutColumn(i);

            for (int j = 1; j < 14; j +=1) {
                TextView textView1 = new TextView(getContext());

                textView1.setId(id);
                id++;
                textView1.setText("");
                textView1.setMaxLines(5);
                textView1.setEllipsize(TextUtils.TruncateAt.END);
                textView1.setBackgroundColor(Color.parseColor("#F0FFFF"));
                textView1.setGravity(Gravity.CENTER);
                textView1.setVisibility( textView1.VISIBLE);


                GridLayout.LayoutParams params1 = new GridLayout.LayoutParams();
                params1.rowSpec = GridLayout.spec( j, 1,1);
                params1.setMargins(5,6,6,5);
                params1.width = GridLayout.LayoutParams.MATCH_PARENT;
                params1.height = 0;

                gridLayout.addView(textView1, params1);
            }

        }

    }
    public  void fuyuan() {


        for(int i=1; i<=13*7 ;i++){
View root=binding.getRoot();
            TextView textView1=root.findViewById(i);
            textView1.setText("");
            textView1.setMaxLines(5);
            textView1.setEllipsize(TextUtils.TruncateAt.END);
            textView1.setBackgroundColor(Color.parseColor("#F0FFFF"));
            textView1.setGravity(Gravity.CENTER);
            textView1.setVisibility( textView1.VISIBLE);


            GridLayout.LayoutParams params1 = new GridLayout.LayoutParams();
            params1.rowSpec = GridLayout.spec( i%13==0?13:i%13 , 1,1);
            params1.setMargins(5,6,6,5);
            params1.width = GridLayout.LayoutParams.MATCH_PARENT;
            params1.height = 0;

            textView1.setLayoutParams(params1);
        }

    }



    public void applyDraw(DBHelper dbHelper) {

        //从数据库拿到课程数据保存在链表

        View root=binding.getRoot();
        List<Classes> classes = query(dbHelper);

        for (Classes aClass : classes) {
            //第几节课
            int i = utils.gettime(aClass.getC_starttime() );        //starttime
            int endtime = utils.gettime(aClass.getC_endtime() );

            //类型
            String slabel = aClass.getC_label();

            //星期几
            int j = utils.getDay(aClass.getC_day());

            //第几周
            int startweek =  utils.getweek(aClass.getC_startweek() );
            int endweek =  utils.getweek(aClass.getC_endweek() );
            //获取此课程对应TextView的id
            TextView Class =root.findViewById((j - 1) * 13 + (i - 1) + 1);

            if(utils.getweek(aClass.getC_startweek())<=weekc && utils.getweek(aClass.getC_endweek())>=weekc)
            {
                Class.setBackgroundColor(Color.rgb(1, 250, 250));

                switch (slabel)
                {
                    case "课程": {
                        Class.setBackgroundColor(Color.rgb(1, 250, 250));
                        break;
                    }
                    case "考试":{
                        Class.setBackgroundColor(Color.rgb(250, 150, 150));
                        break;
                    }
                    case "日程":{
                        Class.setBackgroundColor(Color.rgb(15, 250, 150));
                        break;
                    }
                    case "其他":{
                        Class.setBackgroundColor(Color.rgb(100, 100, 100));
                        break;
                    }
                }

                for(int x=(j - 1) * 13 + i + 1 ; x <= (j - 1) * 13 + ((i - 1) + 1)+ utils.gettime(aClass.getC_endtime())-utils.gettime(aClass.getC_starttime()); x++ )
                {TextView del = root.findViewById(x);
                    del.setVisibility( del.GONE);

                }

                GridLayout.LayoutParams params2 = new GridLayout.LayoutParams();
                params2.rowSpec = GridLayout.spec( utils.gettime(aClass.getC_starttime()), 1,utils.gettime(aClass.getC_endtime())-utils.gettime(aClass.getC_starttime())  + 1);
                params2.setMargins(5,6+ 3 * (utils.gettime(aClass.getC_endtime())- utils.gettime(aClass.getC_starttime())),6 + 3* (utils.gettime(aClass.getC_endtime())- utils.gettime(aClass.getC_starttime())) , 5);
                params2.width = GridLayout.LayoutParams.MATCH_PARENT;
                params2.height = 0;
                Class.setLayoutParams(params2);

                //课程表信息映射出来
                Class.setText(aClass.getC_name());

                //触碰此课程框触发
                Intent intent = new Intent();
                intent.putExtra("sub",aClass.getC_name());
                intent.putExtra("starttime",aClass.getC_starttime());
                intent.putExtra("endtime",aClass.getC_endtime());
                intent.putExtra("startweek",aClass.getC_startweek());
                intent.putExtra("endweek",aClass.getC_endweek());
                intent.putExtra("label",aClass.getC_label());
                intent.putExtra("teacher",aClass.getC_teacher());
                intent.putExtra("day",aClass.getC_day());
                intent.putExtra("id",aClass.getC_id());


                intent.setClass(getContext(), Detail.class);

                //  Class.setOnTouchListener(this);
                Class.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(intent);
                    }
                });


            }



            //判断如果课程星期==当前星期，如此课程信息和当前都是星期二就把背景颜色更换。
            //     Date date = new Date();
            //   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
            //  if (aClass.getC_day().equals(simpleDateFormat.format(date).toString())) {
            //     Class.setBackgroundColor(Color.rgb(100, 250, 204));
//
            // }


            // GridLayout gridLayout2;
          /*  gridLayout = LayoutColumn(utils.getDay(aClass.getC_day()));

                Class.setMaxLines(5);
                Class.setEllipsize(TextUtils.TruncateAt.END);
                Class.setBackgroundColor(Color.parseColor("#F0FFFF"));
                Class.setGravity(Gravity.CENTER);*/

            //////////////


        }

    }

    @SuppressLint("Range")
    public List<Classes> query(DBHelper dbHelper) {

        List<Classes> classes = new ArrayList<>(300);
        // 通过DBHelper类获取一个读写的SQLiteDatabase对象
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 参数1：table_name
        // 参数2：columns 要查询出来的列名。相当于 select  *** from table语句中的 ***部分
        // 参数3：selection 查询条件字句，在条件子句允许使用占位符“?”表示条件值
        // 参数4：selectionArgs ：对应于 selection参数 占位符的值
        // 参数5：groupby 相当于 select *** from table where && group by ... 语句中 ... 的部分
        // 参数6：having 相当于 select *** from table where && group by ...having %%% 语句中 %%% 的部分
        // 参数7：orderBy ：相当于 select  ***from ？？  where&& group by ...having %%% order by@@语句中的@@ 部分，如： personid desc（按person 降序）
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        // 将游标移到开头
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) { // 游标只要不是在最后一行之后，就一直循环

            if (!cursor.getString(cursor.getColumnIndex("c_day")).equals("0")) {
                Classes Class = new Classes();

                Class.setC_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex("c_id"))));
                Class.setC_name(cursor.getString(cursor.getColumnIndex("c_name")));
                Class.setC_starttime(cursor.getString(cursor.getColumnIndex("c_starttime")));
                Class.setC_endtime(cursor.getString(cursor.getColumnIndex("c_endtime")));
                Class.setC_startweek(cursor.getString(cursor.getColumnIndex("c_startweek")));
                Class.setC_endweek(cursor.getString(cursor.getColumnIndex("c_endweek")));
                Class.setC_label(cursor.getString(cursor.getColumnIndex("c_label")));
                Class.setC_day(cursor.getString(cursor.getColumnIndex("c_day")));
                Class.setC_teacher(cursor.getString(cursor.getColumnIndex("c_teacher")));

                classes.add(Class);
            }

            // 将游标移到下一行
            cursor.moveToNext();

        }

        db.close();
        return classes;
    }

    //@Override
   // @SuppressLint("Range")
    public boolean onCreateOptionsMenu(Menu menu) {


       getActivity().getMenuInflater().inflate(R.menu.menu, menu);


        MenuItem menuItem=menu.findItem(R.id.action_menu);

        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent();
                intent.setClass(getContext() ,DialogModal.class);
                startActivity(intent);
                return true;
            }
        });

        //return  super.onCreateOptionsMenu(menu,getActivity().getMenuInflater());
       return false;
    }

    @SuppressLint("Range")
    // @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {


        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN: {
                TextView textView = (TextView) view;


                DBHelper dbHelper = new DBHelper(getContext(), DB_NAME, null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // 参数1：table_name
                // 参数2：columns 要查询出来的列名。相当于 select  *** from table语句中的 ***部分
                // 参数3：selection 查询条件字句，在条件子句允许使用占位符“?”表示条件值
                // 参数4：selectionArgs ：对应于 selection参数 占位符的值
                // 参数5：groupby 相当于 select *** from table where && group by ... 语句中 ... 的部分
                // 参数6：having 相当于 select *** from table where && group by ...having %%% 语句中 %%% 的部分
                // 参数7：orderBy ：相当于 select  ***from ？？  where&& group by ...having %%% order by@@语句中的@@ 部分，如： personid desc（按person 降序）
                //        Cursor cursor = db.query(TABLE_NAME, null, "c_id=?", new String[]{String.valueOf(textView.getId())}, null, null, null);
                Cursor cursor = db.query(TABLE_NAME, null, "c_id=?", new String[]{String.valueOf(textView.getId())}, null, null, null);

                // 将游标移到开头
                cursor.moveToFirst();
                if (!cursor.isAfterLast()) {
                    Classes Class = new Classes();
                    System.out.println(textView.getId());
                    System.out.println(cursor.getString(cursor.getColumnIndex("c_name")));

                    Intent intent = new Intent();
                    intent.putExtra("name", cursor.getString(cursor.getColumnIndex("c_name")));
                    intent.putExtra("time", cursor.getString(cursor.getColumnIndex("c_time")));
                    intent.putExtra("day", cursor.getString(cursor.getColumnIndex("c_day")));
                    intent.putExtra("teacher", cursor.getString(cursor.getColumnIndex("c_teacher")));
                    intent.setClass(getContext(), Detail.class);
                    startActivity(intent);

                }
                return true;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                Intent intent = new Intent(getContext(), ScheduleFragment.class);
                startActivity(intent);
                break;
            }
        }

        return false;
    }
    ///////////////
}