package com.jsojs.mywalletmodule.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.jsojs.mywalletmodule.api.ResponseCallBack;
import com.jsojs.mywalletmodule.api.WalletApi;
import com.jsojs.mywalletmodule.api.WalletApiImpl;
import com.jsojs.mywalletmodule.bean.BindBankList;
import com.jsojs.mywalletmodule.bean.SerialNo;
import com.jsojs.mywalletmodule.contract.WithdrawContract;
import com.jsojs.mywalletmodule.modle.ApiResponse;
import com.jsojs.mywalletmodule.util.MyToken;

/**
 * Created by root on 16-10-27.
 */

public class WithdrawPresenter implements WithdrawContract.Presenter {
    private Context context;
    private WithdrawContract.View view;
    private WalletApi mWalletApi;

    public WithdrawPresenter(Context context, WithdrawContract.View view) {
        this.context = context;
        this.view = view;
        mWalletApi = WalletApiImpl.getInstance(context);
    }

    @Override
    public void getBindBank() {
        view.showLoading();
        mWalletApi.queryBindBankList(MyToken.getMyToken(context), new ResponseCallBack<BindBankList>() {
            @Override
            public void callBack(ApiResponse<BindBankList> response) {
                view.hideLoading();
                if(response.isTokenOut()) {
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    view.getBankSuccess(response.getData().getBank());
//                    if(response.getData().getBank()==null||response.getData().getBank().size()==0){
//                        view.toAddBindBank();
//                    }
                }else {
                    view.showToast(response.getMsg());
                    view.getBankFailure();
                }
            }
        });
    }

    @Override
    public void getCode(String cardId, String amount) {
        if(TextUtils.isEmpty(cardId)) {
            view.showToast("选择银行卡");
            return;
        }
        if(TextUtils.isEmpty(amount)) {
            view.showToast("输入金额");
            return;
        }
        if(Double.parseDouble(amount)<=0){
            view.showToast("金额应该大于0");
            return;
        }
        view.showLoading();
        mWalletApi.withdrawalsCode(MyToken.getMyToken(context), cardId, amount, new ResponseCallBack<SerialNo>() {
            @Override
            public void callBack(ApiResponse<SerialNo> response) {
                view.hideLoading();
                if(response.isTokenOut()){
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    view.showToast("发送成功");
                    view.getCodeSuccess(response.getData().getSerialNo());
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });
    }

    @Override
    public void withdraw(String cardId, String serialNo, String smsCode) {
        if(TextUtils.isEmpty(cardId)) {
            view.showToast("选择银行卡");
            return;
        }
        if(TextUtils.isEmpty(serialNo)) {
            view.showToast("先获取验证码");
            return;
        }
        if(TextUtils.isEmpty(smsCode)) {
            view.showToast("输入验证码");
            return;
        }
        view.showLoading();
        mWalletApi.withdrawals(MyToken.getMyToken(context), cardId, serialNo, smsCode, new ResponseCallBack() {
            @Override
            public void callBack(ApiResponse response) {
                view.hideLoading();
                if(response.isTokenOut()) {
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    view.withdrawSuccess();
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });
    }
}
