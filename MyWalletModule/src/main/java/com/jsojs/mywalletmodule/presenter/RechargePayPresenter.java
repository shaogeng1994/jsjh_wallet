package com.jsojs.mywalletmodule.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.jsojs.mywalletmodule.api.ResponseCallBack;
import com.jsojs.mywalletmodule.api.WalletApi;
import com.jsojs.mywalletmodule.api.WalletApiImpl;
import com.jsojs.mywalletmodule.bean.DateTime;
import com.jsojs.mywalletmodule.bean.RechargeOrder;
import com.jsojs.mywalletmodule.contract.RechargePayContract;
import com.jsojs.mywalletmodule.modle.ApiResponse;
import com.jsojs.mywalletmodule.util.MyToken;

/**
 * Created by root on 16-10-26.
 */

public class RechargePayPresenter implements RechargePayContract.Presenter {
    private Context context;
    private RechargePayContract.View view;
    private WalletApi mWalletApi;
    private int check = -1;

    public RechargePayPresenter(Context context, RechargePayContract.View view) {
        this.context = context;
        this.view = view;
        mWalletApi = WalletApiImpl.getInstance(context);
    }

    @Override
    public void getCode(String orderNumber, String bindId, String bankCode) {
        if(TextUtils.isEmpty(orderNumber)) {
            view.showToast("找不到订单号");
            return;
        }
        if(TextUtils.isEmpty(bindId)) {
            view.showToast("选择银行卡");
            return;
        }
        if(TextUtils.isEmpty(bankCode)) {
            view.showToast("选择银行卡");
            return;
        }
        view.showLoading();
        mWalletApi.rechargePabCode(MyToken.getMyToken(context), orderNumber, bindId, bankCode, new ResponseCallBack<DateTime>() {
            @Override
            public void callBack(ApiResponse<DateTime> response) {
                view.hideLoading();
                if(response.isTokenOut()) {
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    view.getCodeSuccess(response.getData().getDateTime());
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });
    }

    @Override
    public void mode(int i) {
        check = i;
    }

    @Override
    public void checkPayMode(int i) {
        switch (i){
            case 0:
                view.doQuickPay();
                return;
            case 1:
                view.doOnlinePay();
                return;
            case 2:
                view.doOnlinePay();
                return;
        }
    }

    @Override
    public void quickPay(String orderNumber, String bindId, String dateTime, String phoneCode) {
        if(TextUtils.isEmpty(orderNumber)) {
            view.showToast("找不到订单");
            return;
        }
        if(TextUtils.isEmpty(bindId)) {
            view.showToast("选择银行卡");
            return;
        }
        if(TextUtils.isEmpty(dateTime)) {
            view.showToast("请先获取验证码");
            return;
        }
        if(TextUtils.isEmpty(phoneCode)) {
            view.showToast("输入验证码");
            return;
        }
        view.showLoading();
        mWalletApi.rechargePabPay(MyToken.getMyToken(context), orderNumber, bindId, dateTime, phoneCode, new ResponseCallBack() {
            @Override
            public void callBack(ApiResponse response) {
                view.hideLoading();
                if(response.isTokenOut()) {
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    view.quickPaySuccess();
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });
    }

    @Override
    public void onlinePay(String orderNumber, String plantBankId) {
        if(TextUtils.isEmpty(orderNumber)){
            view.showToast("找不到订单号");
            return;
        }
        if(TextUtils.isEmpty(plantBankId)){
            view.showToast("请选择绑定银行卡");
            return;
        }
        view.showLoading();
        mWalletApi.rechargeToPabPay(MyToken.getMyToken(context), orderNumber, plantBankId, new ResponseCallBack<String>() {
            @Override
            public void callBack(ApiResponse<String> response) {
                view.hideLoading();
                if(response.isTokenOut()) {
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    view.payOnlineSuccess(response.getData());
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });
    }

    @Override
    public void PayOrder(String orderId) {
        if(TextUtils.isEmpty(orderId)){
            view.showToast("找不到订单号");
            return;
        }
        view.showLoading();
        mWalletApi.rechargePay(MyToken.getMyToken(context), orderId, new ResponseCallBack<RechargeOrder>() {
            @Override
            public void callBack(ApiResponse<RechargeOrder> response) {
                view.hideLoading();
                if(response.isTokenOut()) {
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    if(check == 1) {
                        if(response.getData().getParinfo()==null||response.getData().getParinfo().size()==0){
                            view.showToast("没有绑定银行卡，请用储蓄卡或信用卡支付");
                            view.noBindBank();
                            return;
                        }
                        view.selectBindBank(response.getData().getParinfo().get(0));
                    }else if(check == 2) {
                        view.selectBindBank(response.getData().getQuick_payment().get(0));
                    }else {
                        view.selectBindBank(response.getData().getQuick_payment().get(1));
                    }
                    view.getOrderSuccess(response.getData());
                }else {
                    view.showToast(response.getMsg());
                    view.getOrderFailure();
                }
            }
        });
    }
}
