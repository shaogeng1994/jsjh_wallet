package com.jsojs.mywalletmodule.contract;

import com.jsojs.mywalletmodule.bean.PabBindCard;
import com.jsojs.mywalletmodule.bean.Payment;
import com.jsojs.mywalletmodule.bean.QuickPayment;
import com.jsojs.mywalletmodule.bean.RechargeOrder;

import java.util.List;
import java.util.Map;

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
        void selectBindBank(PabBindCard pabBindCard);
        void noBindBank();
        void getOrderFailure();
        void getBankImgSuccess(Map banks);
        void getPaymentInfoSuccess(Payment payment);
        void getPaymentInfoFailure();
    }
    interface Presenter {
        void getCode(String orderNumber, String bindId, String bankCode);
        void mode(int i);
        void checkPayMode(int i);
        void quickPay(String paymentSn, String bindId, String dateTime, String phoneCode);
        void onlinePay(String orderNumber, String plantBankId);
        void PayOrder(String orderId);
        void getBankImg();
        void getPaymentInfo(String paymentSn);
        void debitCardPay(String paymentSn,String plantBankId);
        void creditCardPay(String paymentSn,String plantBankId);
    }
}
