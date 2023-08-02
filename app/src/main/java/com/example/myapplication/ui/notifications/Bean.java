package com.example.myapplication.ui.notifications;

public class Bean {
    private String name;
    private int _id;
    private int position;
    private boolean check_on;
    private long time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isCheckOn() {
        return check_on;
    }

    public void setCheckOn(boolean check_on) {
        this.check_on = check_on;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
