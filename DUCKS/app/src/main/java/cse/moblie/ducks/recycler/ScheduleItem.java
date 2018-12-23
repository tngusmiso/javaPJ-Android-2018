package cse.moblie.ducks.recycler;

import android.graphics.Bitmap;

public class ScheduleItem {

    int Type;

    String month;       //writer        //writer
    String date;        //writtenDate   //comment
    String startTime;   //writtenTime   //date
    String endTime;     //dueTime       //time
    String title;       //title
    String address;     //content

    String duck;
    String comments;

    public ScheduleItem(int Type, String month, String date, String startTime, String endTime, String title, String address, String duck, String comments) {
        this.Type = Type;
        this.month = month;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.address = address;
        this.duck = duck;
        this.comments = comments;
    }


    public int getType() {return Type;}

    public void setType(int Type) { this.Type = Type;}

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

    public String getDuck() { return duck;}

    public void setDuck(String duck) {
        this.duck = duck;
    }

    public String getComments() {return comments;}

    public void setComments(String comments) { this.comments = comments;}

}
