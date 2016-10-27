package com.jsojs.mywalletmodule.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.api.ResponseCallBack;
import com.jsojs.mywalletmodule.api.WalletApi;
import com.jsojs.mywalletmodule.api.WalletApiImpl;
import com.jsojs.mywalletmodule.contract.BankCardInfoContract;
import com.jsojs.mywalletmodule.modle.ApiResponse;
import com.jsojs.mywalletmodule.util.MyToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 16-10-26.
 */

public class BankCardInfoPresenter implements BankCardInfoContract.Presenter {
    private Context context;
    private BankCardInfoContract.View view;
    private WalletApi mWalletApi;
    private Map<String,Integer> map = new HashMap<>();

    public BankCardInfoPresenter(Context context, BankCardInfoContract.View view) {
        this.context = context;
        this.view = view;
        this.mWalletApi = WalletApiImpl.getInstance(context);
        pushBankImg();
    }

    @Override
    public void removeBank(String bankIds) {
        if(TextUtils.isEmpty(bankIds)) {
            view.showToast("找不到银行卡");
            return;
        }
        view.showLoading();
        mWalletApi.delBindBank(MyToken.getMyToken(context), bankIds, new ResponseCallBack() {
            @Override
            public void callBack(ApiResponse response) {
                view.hideLoading();
                if(response.isTokenOut()){
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    view.removeBankSuccess();
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });
    }

    @Override
    public void getBankImg() {
        view.bankImg(map);
    }

    private void pushBankImg(){
        map.put("工商银行", R.mipmap.bank_icbc);
        map.put("农业银行",R.mipmap.bank_abc);
        map.put("中国银行",R.mipmap.bank_boc);
        map.put("建设银行",R.mipmap.bank_ccb);
        map.put("交通银行",R.mipmap.bank_bcm);
        map.put("招商银行",R.mipmap.bank_cmb);
        map.put("中信银行",R.mipmap.bank_citic);
        map.put("平安银行",R.mipmap.bank_pab);
        map.put("兴业银行",R.mipmap.bank_cib);
        map.put("浦发银行",R.mipmap.bank_psdb);
        map.put("光大银行",R.mipmap.bank_ceb);
        map.put("民生银行",R.mipmap.bank_cmbc);
        map.put("邮政储蓄银行",R.mipmap.bank_psb);
        map.put("北京银行",R.mipmap.bank_bob);
        map.put("上海银行",R.mipmap.bank_bos);
    }
}
