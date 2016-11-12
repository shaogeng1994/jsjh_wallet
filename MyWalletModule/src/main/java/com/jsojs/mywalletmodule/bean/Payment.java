package com.jsojs.mywalletmodule.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16-11-8.
 */

public class Payment implements Serializable {
    private String payment_sn;
    private String paymenttype;
    private String paymenttypename;
    private String paytime;
    private String orderamount;
    private String payamount;
    private String ispayall;
    private String payrate;
    private String accountamount;
    private String paymtel;
    private String order_sn;
    private Map<String,String> allowpaymode;
    private List<PabBindCard> pabkjbindcardlist;
    private List<PabBindCard> bankList;

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public List<PabBindCard> getBankList() {
        return bankList;
    }

    public void setBankList(List<PabBindCard> bankList) {
        this.bankList = bankList;
    }

    public String getPayment_sn() {
        return payment_sn;
    }

    public void setPayment_sn(String payment_sn) {
        this.payment_sn = payment_sn;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public String getPaymenttypename() {
        return paymenttypename;
    }

    public void setPaymenttypename(String paymenttypename) {
        this.paymenttypename = paymenttypename;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(String orderamount) {
        this.orderamount = orderamount;
    }

    public String getPayamount() {
        return payamount;
    }

    public void setPayamount(String payamount) {
        this.payamount = payamount;
    }

    public String getIspayall() {
        return ispayall;
    }

    public void setIspayall(String ispayall) {
        this.ispayall = ispayall;
    }

    public String getPayrate() {
        return payrate;
    }

    public void setPayrate(String payrate) {
        this.payrate = payrate;
    }

    public String getAccountamount() {
        return accountamount;
    }

    public void setAccountamount(String accountamount) {
        this.accountamount = accountamount;
    }

    public String getPaymtel() {
        return paymtel;
    }

    public void setPaymtel(String paymtel) {
        this.paymtel = paymtel;
    }

    public Map<String, String> getAllowpaymode() {
        return allowpaymode;
    }

    public void setAllowpaymode(Map<String, String> allowpaymode) {
        this.allowpaymode = allowpaymode;
    }

    public List<PabBindCard> getPabkjbindcardlist() {
        return pabkjbindcardlist;
    }

    public void setPabkjbindcardlist(List<PabBindCard> pabkjbindcardlist) {
        this.pabkjbindcardlist = pabkjbindcardlist;
    }
}
