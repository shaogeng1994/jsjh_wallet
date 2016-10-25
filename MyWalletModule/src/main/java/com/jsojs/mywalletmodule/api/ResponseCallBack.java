package com.jsojs.mywalletmodule.api;


import com.jsojs.mywalletmodule.modle.ApiResponse;

/**
 * Created by root on 16-10-24.
 */

public interface ResponseCallBack<T> {
    public void callBack(ApiResponse<T> response);
}
