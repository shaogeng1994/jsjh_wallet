package com.jsojs.mywalletmodule.contract;

import com.jsojs.mywalletmodule.bean.QuickPayment;
import com.jsojs.mywalletmodule.bean.RechargeOrder;

import java.util.List;

/**
 * Created by root on 16-10-26.
 */

public interface RechargePayContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showToast(String msg);
        void doTokenOut();
        void getCodeSuccess(String dateTime);
        void getOrderSuccess(RechargeOrder rechargeOrder);
        void quickPaySuccess();
        void payOnlineSuccess(String html);
        void doQuickPay();
        void doOnlinePay();
        void selectBindBank(QuickPayment quickPayment);
        void noBindBank();
        void getOrderFailure();
    }
    interface Presenter {
        void getCode(String orderNumber, String bindId, String bankCode);
        void mode(int i);
        void checkPayMode(int i);
        void quickPay(String orderNumber, String bindId, String dateTime, String phoneCode);
        void onlinePay(String orderNumber, String plantBankId);
        void PayOrder(String orderId);
    }
}
