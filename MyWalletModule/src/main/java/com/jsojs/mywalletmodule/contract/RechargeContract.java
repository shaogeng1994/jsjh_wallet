package com.jsojs.mywalletmodule.contract;

import com.jsojs.mywalletmodule.bean.RechargeOrder;

/**
 * Created by root on 16-10-26.
 */

public interface RechargeContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showToast(String msg);
        void doTokenOut();
        void submitSuccess(String paymentSn);
    }
    interface Presenter {
        void submitOrder(String amount);
    }
}
