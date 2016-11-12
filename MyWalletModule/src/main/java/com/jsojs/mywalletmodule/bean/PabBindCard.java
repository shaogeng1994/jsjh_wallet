package com.jsojs.mywalletmodule.bean;

import java.io.Serializable;

/**
 * Created by root on 16-11-8.
 */

public class PabBindCard implements Serializable {
    private String bindId;
    private String accountNo;
    private String plantBankName;
    private String telephone;
    private String plantId;
    private String plantBankId;
    private String id;
    private String bankname;
    private String cardtype;
    private String singlequota;
    private String dailylimit;
    private String bankcode;

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPlantBankName() {
        return plantBankName;
    }

    public void setPlantBankName(String plantBankName) {
        this.plantBankName = plantBankName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public String getPlantBankId() {
        return plantBankId;
    }

    public void setPlantBankId(String plantBankId) {
        this.plantBankId = plantBankId;
    }

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

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
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

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }
}
