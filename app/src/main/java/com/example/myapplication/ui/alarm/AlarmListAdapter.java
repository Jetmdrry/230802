package com.example.myapplication.ui.alarm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.notifications.Bean;

import java.util.List;
import java.util.Locale;

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.AlarmListViewHolder> {
    @NonNull
    private List<AlarmBean> data;
    private Context context;

    public AlarmListAdapter(@NonNull List<AlarmBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public AlarmListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.alarm_item, null);
        return new AlarmListViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AlarmListAdapter.AlarmListViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tv.setText(String.format(Locale.CHINA,"%02d",data.get(position).getHour())  + ":" + String.format(Locale.CHINA,"%02d",data.get(position).getMinute()));
        holder.sw.setChecked(data.get(position).isSwitchOn());
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public static class AlarmListViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        TextView tv;
        SwitchCompat sw;

        public AlarmListViewHolder(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.tv_alarm);
            sw = itemView.findViewById(R.id.sw_alarm);

            itemView.setOnClickListener(this);
            sw.setOnCheckedChangeListener(this);
        }

        public void onClick(View view) {
            if (mOnRecyclerItemClickListener != null) {
                mOnRecyclerItemClickListener.OnRecyclerItemClick(getAbsoluteAdapterPosition());
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (mOnRecyclerItemCheckedChangeListener != null) {
                mOnRecyclerItemCheckedChangeListener.OnRecyclerItemCheckedChange(getAbsoluteAdapterPosition(), b);
            }
        }


    }

    public static OnRecyclerItemCheckedChangeListener mOnRecyclerItemCheckedChangeListener;

    public void setOnRecyclerItemCheckedChangeListener(OnRecyclerItemCheckedChangeListener listener) {
        mOnRecyclerItemCheckedChangeListener = listener;
    }

    public interface OnRecyclerItemCheckedChangeListener {
        void OnRecyclerItemCheckedChange(int position, boolean b);
    }

    public static OnRecyclerItemClickListener mOnRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener listener) {
        mOnRecyclerItemClickListener = listener;
    }

    public interface OnRecyclerItemClickListener {
        void OnRecyclerItemClick(int position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeData(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<AlarmBean> newData) {
        int count = data.size();
        data = newData;
        notifyItemRangeRemoved(0, count);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void switchOn(int position) {
        AlarmBean alarm = data.get(position);
        alarm.setSwitch_on(true);
        data.set(position, alarm);
        notifyDataSetChanged();
    }


}
