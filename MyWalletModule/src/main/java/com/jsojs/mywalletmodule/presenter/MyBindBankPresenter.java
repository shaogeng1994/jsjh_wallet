package com.jsojs.mywalletmodule.presenter;

import android.content.Context;

import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.api.ResponseCallBack;
import com.jsojs.mywalletmodule.api.WalletApi;
import com.jsojs.mywalletmodule.api.WalletApiImpl;
import com.jsojs.mywalletmodule.bean.BindBankList;
import com.jsojs.mywalletmodule.contract.MyBindBankContract;
import com.jsojs.mywalletmodule.modle.ApiResponse;
import com.jsojs.mywalletmodule.util.MyToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 16-10-25.
 */

public class MyBindBankPresenter implements MyBindBankContract.Presenter {
    private MyBindBankContract.View view;
    private WalletApi mWalletApi;
    private Context context;
    private Map<String,Integer> map = new HashMap<>();

    public MyBindBankPresenter(Context context, MyBindBankContract.View view) {
        this.context = context;
        this.view = view;
        mWalletApi = WalletApiImpl.getInstance(context);
        pushBankImg();
    }

    @Override
    public void getBankList() {
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
                    view.showBankList(response.getData());
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });

    }

    @Override
    public void getBankImg() {
        pushBankImg();
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
