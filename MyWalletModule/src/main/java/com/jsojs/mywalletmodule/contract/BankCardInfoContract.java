package com.jsojs.mywalletmodule.contract;

import java.util.Map;

/**
 * Created by root on 16-10-26.
 */

public interface BankCardInfoContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showToast(String msg);
        void removeBankSuccess();
        void doTokenOut();
        void bankImg(Map<String,Integer> map);
    }
    interface Presenter {
        void removeBank(String bankIds);
        void getBankImg();
    }
}
