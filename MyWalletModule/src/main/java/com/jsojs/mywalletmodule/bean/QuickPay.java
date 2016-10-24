package com.jsojs.mywalletmodule.bean;

/**
 * Created by Administrator on 2016/7/20.
 */
public class QuickPay {
    private String bankCode;
    private String bankName;
    private String cardType;
    private String id;
    private String dailylimit;
    private String singlequota;
    private String bindId;
    private String telephone;

    private String bankurl;

    public String getBankurl() {
        return bankurl;
    }

    public void setBankurl(String bankurl) {
        this.bankurl = bankurl;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDailylimit() {
        return dailylimit;
    }

    public void setDailylimit(String dailylimit) {
        this.dailylimit = dailylimit;
    }

    public String getSinglequota() {
        return singlequota;
    }

    public void setSinglequota(String singlequota) {
        this.singlequota = singlequota;
    }
}
