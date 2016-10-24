package com.jsojs.mywalletmodule.api;

import com.jsojs.mywalletmodule.modle.Respone;

/**
 * Created by root on 16-10-24.
 */

public interface ResponeCallBack<T> {
    public void callBack(Respone<T> respone);
}
