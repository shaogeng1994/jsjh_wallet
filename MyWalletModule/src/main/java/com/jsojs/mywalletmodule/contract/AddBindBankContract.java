package com.jsojs.mywalletmodule.contract;

import com.jsojs.mywalletmodule.bean.BankList;

/**
 * Created by root on 16-10-25.
 */

public interface AddBindBankContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showToast(String msg);
        void showBankList(BankList bankList);
        void doTokenOut();
        void sendSuccess();
        void addBankSuccess();
    }

    interface Presenter {
        void getBankList();
        void getCode(String custName,String custId,String bankCard,String bankCode,String bankName,String mobile);
        void addBank(String custName, String custId, String bankCard, String bankCode, String bankName, String mobile, String smsCode);
    }
}
