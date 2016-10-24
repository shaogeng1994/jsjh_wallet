package com.jsojs.mywalletmodule.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */
public class QuickPayOrder {
    private String orderNumber;
    private String orderAmount;
    private String mobile;
    private String mobiled;
    private List<QuickPay> quickPayList;
    private String totalBalance;

    private String payAmount;

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobiled() {
        return mobiled;
    }

    public void setMobiled(String mobiled) {
        this.mobiled = mobiled;
    }

    public List<QuickPay> getQuickPayList() {
        return quickPayList;
    }

    public void setQuickPayList(List<QuickPay> quickPayList) {
        this.quickPayList = quickPayList;
    }
}
