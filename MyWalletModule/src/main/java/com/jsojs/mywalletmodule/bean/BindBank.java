package com.jsojs.mywalletmodule.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/28.
 */
public class BindBank implements Serializable {
    private String id;
    private String bankname;
    private String mobile;
    private String bankcard;
    private String bankcode;
    private String singlequota;
    private String dailylimit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getSinglequota() {
        return singlequota;
    }

    public void setSinglequota(String singlequota) {
        this.singlequota = singlequota;
    }

    public String getDailylimit() {
        return dailylimit;
    }

    public void setDailylimit(String dailylimit) {
        this.dailylimit = dailylimit;
    }
}
