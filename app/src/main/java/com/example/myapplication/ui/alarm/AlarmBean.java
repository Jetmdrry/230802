package com.example.myapplication.ui.alarm;

public class AlarmBean {
    private int hour;
    private int minute;
    private int _id;
    private boolean switch_on;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public boolean isSwitchOn() {
        return switch_on;
    }

    public void setSwitch_on(boolean switch_on) {
        this.switch_on = switch_on;
    }
}
