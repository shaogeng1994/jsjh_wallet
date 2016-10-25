package com.jsojs.mywalletmodule.contract;

import com.jsojs.mywalletmodule.bean.BindBankList;

import java.util.Map;

/**
 * Created by root on 16-10-25.
 */

public interface MyBindBankContract {
    interface View {
        void showLoading();
        void showToast(String msg);
        void hideLoading();
        void showBankList(BindBankList bindBankList);
        void doTokenOut();
        void bankImg(Map<String,Integer> map);
    }

    interface Presenter {
        void getBankList();
        void getBankImg();
    }
}
