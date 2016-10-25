package com.jsojs.mywalletmodule.bean;

import java.io.Serializable;

/**
 * Created by root on 16-9-19.
 */
public class DateTime implements Serializable {
    private String dateTime;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
