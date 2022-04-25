package com.example.vis.model;

public class UoloadTimer_Data {
    String email,starttime,endtime,date,status;

    public UoloadTimer_Data(String email, String starttime, String endtime, String date, String status) {
        this.email = email;
        this.starttime = starttime;
        this.endtime = endtime;
        this.date = date;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
