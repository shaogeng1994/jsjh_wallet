package com.jsojs.mywalletmodule.modle;

import java.io.Serializable;

/**
 * Created by root on 16-10-24.
 */

public class ApiResponse<T> implements Serializable {
    private String code;
    private T data;
    private T info;
    private T payment;
    private String msg;
    private String ret;

    public T getPayment() {
        return payment;
    }

    public void setPayment(T payment) {
        this.payment = payment;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }


    public boolean isSuccess(){
        if(ret!=null){
            if(ret.equals("200"))return true;
            else return false;
        }else if(code!=null){
            if(code.equals("1"))return true;
            else return false;
        }else return false;
    }

    public boolean isTokenOut(){
        if(code!=null){
            if(code.equals("101")||code.equals("102")||code.equals("103"))return true;
            else return false;
        }else return false;
    }
}
