package com.example.myapplication.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Bean> data;

    private Context context;

    public MyAdapter(List<Bean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.memo_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv.setText(data.get(position).getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        holder.tvTime.setText(dateFormat.format(data.get(position).getTime()));
        if(data.get(position).getTime()>System.currentTimeMillis()){
            if (data.get(position).isCheckOn()){
                holder.itemView.setBackgroundColor(Color.GREEN);
            }else {
                holder.itemView.setBackgroundColor(Color.WHITE);
            }
        }else {
            if (data.get(position).isCheckOn()){
                holder.itemView.setBackgroundColor(Color.GRAY);
            }else {
                holder.itemView.setBackgroundColor(Color.RED);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv;
        private TextView tvTime;
        private ImageButton btn_check;
        private ImageButton btn_clear;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            tvTime = itemView.findViewById(R.id.tv_memotime);
            btn_check = itemView.findViewById(R.id.btn_check);
            btn_clear = itemView.findViewById(R.id.btn_clear);

            itemView.setOnClickListener(this);
            btn_check.setOnClickListener(this);
            btn_clear.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnRecyclerItemClickListener != null) {
                mOnRecyclerItemClickListener.OnRecyclerItemClick(getAbsoluteAdapterPosition(),view.getId());
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeData(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void checkData(int position, boolean b){
        Bean tmp = data.get(position);
        tmp.setCheckOn(b);
        data.set(position,tmp);
        notifyItemChanged(position);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData( List<Bean> newData) {
        int count=data.size();
        data=newData;
        notifyItemRangeRemoved(0,count);
        notifyDataSetChanged();
    }



    public OnRecyclerItemClickListener mOnRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener listener) {
        mOnRecyclerItemClickListener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void OnRecyclerItemClick(int position, int viewId);

    }
}
