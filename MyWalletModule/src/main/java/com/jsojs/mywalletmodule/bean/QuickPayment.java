package com.jsojs.mywalletmodule.bean;

import java.io.Serializable;

/**
 * Created by root on 16-9-19.
 */
public class QuickPayment implements Serializable {
    private String bankcode;
    private String bankname;
    private String cardtype;
    private String dailylimit;
    private String id;
    private String singlequota;
    private String bindId;
    private String telephone;

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getDailylimit() {
        return dailylimit;
    }

    public void setDailylimit(String dailylimit) {
        this.dailylimit = dailylimit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSinglequota() {
        return singlequota;
    }

    public void setSinglequota(String singlequota) {
        this.singlequota = singlequota;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
