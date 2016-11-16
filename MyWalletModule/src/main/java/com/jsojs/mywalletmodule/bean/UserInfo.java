package com.jsojs.mywalletmodule.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/29.
 */
public class UserInfo implements Serializable {
    private String baseNick;
    private String head;
    private String baseGender;
    private String bindEmail;
    private String bindMobile;
    private String custName;
    private String title;
    private String token;
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBaseNick() {
        return baseNick;
    }

    public void setBaseNick(String baseNick) {
        this.baseNick = baseNick;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBaseGender() {
        return baseGender;
    }

    public void setBaseGender(String baseGender) {
        this.baseGender = baseGender;
    }

    public String getBindEmail() {
        return bindEmail;
    }

    public void setBindEmail(String bindEmail) {
        this.bindEmail = bindEmail;
    }

    public String getBindMobile() {
        return bindMobile;
    }

    public void setBindMobile(String bindMobile) {
        this.bindMobile = bindMobile;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
