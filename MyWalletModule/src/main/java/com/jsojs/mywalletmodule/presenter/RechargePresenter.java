package com.jsojs.mywalletmodule.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.jsojs.mywalletmodule.api.ResponseCallBack;
import com.jsojs.mywalletmodule.api.WalletApi;
import com.jsojs.mywalletmodule.api.WalletApiImpl;
import com.jsojs.mywalletmodule.bean.RechargeOrder;
import com.jsojs.mywalletmodule.contract.RechargeContract;
import com.jsojs.mywalletmodule.modle.ApiResponse;
import com.jsojs.mywalletmodule.util.MyToken;

import java.util.Map;

/**
 * Created by root on 16-10-26.
 */

public class RechargePresenter implements RechargeContract.Presenter {
    private Context context;
    private RechargeContract.View view;
    private WalletApi mWalletApi;

    public RechargePresenter(Context context, RechargeContract.View view) {
        this.context = context;
        this.view = view;
        this.mWalletApi = WalletApiImpl.getInstance(context);
    }


    @Override
    public void submitOrder(String amount) {
        if(TextUtils.isEmpty(amount)){
            view.showToast("请出入金额");
            return;
        }
        if(Double.parseDouble(amount)<=0){
            view.showToast("金额应该大于0");
            return;
        }
        view.showLoading();
        mWalletApi.rechargeAdd(MyToken.getMyToken(context), amount, new ResponseCallBack<Map<String,String>>() {
            @Override
            public void callBack(ApiResponse<Map<String,String>> response) {
                view.hideLoading();
                if(response.isTokenOut()){
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    view.submitSuccess(response.getData().get("payment_sn"));
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });
    }
}
