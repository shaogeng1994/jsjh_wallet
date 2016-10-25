package com.jsojs.mywalletmodule.contract;

import com.jsojs.mywalletmodule.bean.WalletMsg;

/**
 * Created by root on 16-10-25.
 */

public interface WalletContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showWalletMsg(WalletMsg walletMsg);
        void doTokenOut();
        void showToast(String msg);
    }

    interface Presenter {
        void getWalletMsg();
    }
}
