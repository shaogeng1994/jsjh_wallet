package com.jsojs.mywalletmodule.contract;

import com.jsojs.mywalletmodule.bean.BindBank;

import java.util.List;

/**
 * Created by root on 16-10-27.
 */

public interface WithdrawContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showToast(String msg);
        void doTokenOut();
        void getBankSuccess(List<BindBank> bindBanks);
        void toAddBindBank();
        void getCodeSuccess(String serialNo);
        void withdrawSuccess();
        void getBankFailure();
        void getBankImgSuccess(int bankImg);
    }
    interface Presenter {
        void getBindBank();
        void getCode(String cardId,String amount);
        void withdraw(String cardId, String serialNo, String smsCode);
        void getBankImg(String bankName);
    }
}
