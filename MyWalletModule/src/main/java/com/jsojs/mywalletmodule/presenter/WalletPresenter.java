package com.jsojs.mywalletmodule.presenter;

import android.content.Context;

import com.jsojs.mywalletmodule.api.ResponseCallBack;
import com.jsojs.mywalletmodule.api.WalletApi;
import com.jsojs.mywalletmodule.api.WalletApiImpl;
import com.jsojs.mywalletmodule.bean.WalletMsg;
import com.jsojs.mywalletmodule.contract.WalletContract;
import com.jsojs.mywalletmodule.modle.ApiResponse;
import com.jsojs.mywalletmodule.util.MyToken;

/**
 * Created by root on 16-10-25.
 */

public class WalletPresenter implements WalletContract.Presenter {
    private WalletContract.View view;
    private WalletApi mWalletApi;
    private Context context;

    public WalletPresenter(Context context,WalletContract.View view){
        mWalletApi = WalletApiImpl.getInstance(context);
        this.view = view;
        this.context = context;
    }

    @Override
    public void getWalletMsg() {
        view.showLoading();
        mWalletApi.queryWallet(MyToken.getMyToken(context), new ResponseCallBack<WalletMsg>() {
            @Override
            public void callBack(ApiResponse<WalletMsg> response) {
                view.hideLoading();
                if(response.isTokenOut()) {
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    view.showWalletMsg(response.getData());
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });
    }
}
