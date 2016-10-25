package com.jsojs.mywalletmodule.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 16-9-30.
 */
public class BankList implements Serializable {
    private String custName;
    private String custId;
    private String bank_baseUrl;
    private List<Bank> bankList;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getBank_baseUrl() {
        return bank_baseUrl;
    }

    public void setBank_baseUrl(String bank_baseUrl) {
        this.bank_baseUrl = bank_baseUrl;
    }

    public List<Bank> getBankList() {
        return bankList;
    }

    public void setBankList(List<Bank> bankList) {
        this.bankList = bankList;
    }
}
