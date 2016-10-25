package com.jsojs.mywalletmodule.bean;

import java.io.Serializable;

/**
 * Created by root on 16-10-25.
 */

public class AddBankResult implements Serializable {
    private String member_id;
    private String bankname;
    private String sbankcode;
    private String bankcard;
    private String created;
    private String deleted;
    private String mobile;
    private String idcode;
    private String frontlogno;
    private String id;
    private String bank_baseUrl;

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getSbankcode() {
        return sbankcode;
    }

    public void setSbankcode(String sbankcode) {
        this.sbankcode = sbankcode;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getFrontlogno() {
        return frontlogno;
    }

    public void setFrontlogno(String frontlogno) {
        this.frontlogno = frontlogno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank_baseUrl() {
        return bank_baseUrl;
    }

    public void setBank_baseUrl(String bank_baseUrl) {
        this.bank_baseUrl = bank_baseUrl;
    }
}
