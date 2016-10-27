package com.jsojs.mywalletmodule.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 16-9-30.
 */
public class RechargeOrder implements Serializable {
    private String order_id;
    private String bank_baseUrl;
    private String orderNumber;
    private String bind_mobile;
    private String pay_amount;
    private List<QuickPayment> quick_payment;
    private List<QuickPayment> parinfo;

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getBind_mobile() {
        return bind_mobile;
    }

    public void setBind_mobile(String bind_mobile) {
        this.bind_mobile = bind_mobile;
    }

    public List<QuickPayment> getParinfo() {
        return parinfo;
    }

    public void setParinfo(List<QuickPayment> parinfo) {
        this.parinfo = parinfo;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getBank_baseUrl() {
        return bank_baseUrl;
    }

    public void setBank_baseUrl(String bank_baseUrl) {
        this.bank_baseUrl = bank_baseUrl;
    }

    public List<QuickPayment> getQuick_payment() {
        return quick_payment;
    }

    public void setQuick_payment(List<QuickPayment> quick_payment) {
        this.quick_payment = quick_payment;
    }
}
