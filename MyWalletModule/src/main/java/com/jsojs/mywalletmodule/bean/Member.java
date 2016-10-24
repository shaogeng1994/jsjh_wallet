package com.jsojs.mywalletmodule.bean;

import java.io.Serializable;

/**
 * Created by root on 16-10-24.
 */

public class Member implements Serializable {
    private String custacctid;
    private String custname;
    private String custid;

    public String getCustacctid() {
        return custacctid;
    }

    public void setCustacctid(String custacctid) {
        this.custacctid = custacctid;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }
}
