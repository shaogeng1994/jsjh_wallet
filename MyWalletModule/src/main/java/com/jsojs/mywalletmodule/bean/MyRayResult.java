package com.jsojs.mywalletmodule.bean;

import java.io.Serializable;

/**
 * Created by root on 16-11-8.
 */

public class MyRayResult implements Serializable {
    private String payhtml;
    private String sign;

    public String getPayhtml() {
        return payhtml;
    }

    public void setPayhtml(String payhtml) {
        this.payhtml = payhtml;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
