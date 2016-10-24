package com.jsojs.mywalletmodule.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 16-10-24.
 */

public class BindBankList implements Serializable {
    private Member member;
    private List<BindBank> bank;
    private String bank_baseUrl;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public List<BindBank> getBank() {
        return bank;
    }

    public void setBank(List<BindBank> bank) {
        this.bank = bank;
    }

    public String getBank_baseUrl() {
        return bank_baseUrl;
    }

    public void setBank_baseUrl(String bank_baseUrl) {
        this.bank_baseUrl = bank_baseUrl;
    }
}
