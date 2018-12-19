package cse.moblie.ducks.recycler;

import android.graphics.Bitmap;

public class ScheduleItem {

    String month;
    String date;
    String startTime;
    String endTime;
    String title;
    String address;

    public ScheduleItem(String month, String date, String startTime, String endTime, String title, String address) {
        this.month = month;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.address = address;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) { this.month = month;}

    public String getDate() {
        return date;
    }

    public void setDate(String date) { this.date = date;}

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) { this.startTime = startTime;}

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {this.endTime = endTime;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title; }

    public String getAddress() { return address;}

    public void setAddress(String address) {
        this.address = address;
    }

}
