package com.jsojs.mywalletmodule.bean;

import java.io.Serializable;

/**
 * Created by root on 16-10-24.
 */

public class WalletMsg implements Serializable {
    private String CustAcctId;
    private String TotalAmount;
    private String TotalBalance;
    private String TotalFreezeAmount;

    public String getCustAcctId() {
        return CustAcctId;
    }

    public void setCustAcctId(String custAcctId) {
        CustAcctId = custAcctId;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getTotalBalance() {
        return TotalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        TotalBalance = totalBalance;
    }

    public String getTotalFreezeAmount() {
        return TotalFreezeAmount;
    }

    public void setTotalFreezeAmount(String totalFreezeAmount) {
        this.TotalFreezeAmount = totalFreezeAmount;
    }
}
