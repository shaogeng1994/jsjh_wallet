package com.jsojs.mywalletmodule.bean;

/**
 * Created by Administrator on 2016/7/21.
 */
public class Bank {
    private String bankCode;
    private String name;
    private String cardType;
    private String dailylimit;
    private String id;
    private String singlequota;
    private String bankUrl;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBankUrl() {
        return bankUrl;
    }

    public void setBankUrl(String bankUrl) {
        this.bankUrl = bankUrl;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
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
}
