package com.example.myapplication.ui.schedule;

public class Classes {

    private int c_id;
    private String c_name;
    private String c_starttime;
    private String c_endtime;
    private String c_startweek;
    private String c_endweek;
    private String c_day;
    private String c_label;
    private String c_teacher;

    @Override
    public String toString() {
        return "Classes{" +
                "c_id=" + c_id +
                ", c_name='" + c_name + '\'' +
                ", c_starttime='" + c_starttime + '\'' +
                ", c_endtime='" + c_endtime + '\'' +
                ", c_startweek='" + c_startweek + '\'' +
                ", c_endweek='" + c_endweek + '\'' +
                ", c_label='" + c_label  + '\'' +
                ", c_day='" + c_day + '\'' +
                '}';
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_starttime() {
        return c_starttime;
    }

    public void setC_starttime(String x) {
        this.c_starttime = x;
    }

    public String getC_endtime() {
        return c_endtime;
    }

    public void setC_endtime(String c_time) {
        this.c_endtime = c_time;
    }

    public String getC_day() {
        return c_day;
    }

    public void setC_day(String c_day) {
        this.c_day = c_day;
    }

    public String getC_startweek() {
        return c_startweek;
    }

    public void setC_startweek(String x) {
        this.c_startweek = x;
    }

    public String getC_endweek() {
        return c_endweek;
    }

    public void setC_endweek(String x) {
        this.c_endweek = x;
    }

    public String getC_label() {
        return c_label;
    }

    public void setC_label(String x) {
        this.c_label = x;
    }

    public String getC_teacher() {
        return c_teacher;
    }

    public void setC_teacher(String x) {
        this.c_teacher = x;
    }
}


