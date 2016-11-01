package com.jsojs.mywalletmodule.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.api.ResponseCallBack;
import com.jsojs.mywalletmodule.api.WalletApi;
import com.jsojs.mywalletmodule.api.WalletApiImpl;
import com.jsojs.mywalletmodule.bean.AddBankResult;
import com.jsojs.mywalletmodule.bean.Bank;
import com.jsojs.mywalletmodule.bean.BankList;
import com.jsojs.mywalletmodule.contract.AddBindBankContract;
import com.jsojs.mywalletmodule.modle.ApiResponse;
import com.jsojs.mywalletmodule.util.MyToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16-10-25.
 */

public class AddBindBankPresenter implements AddBindBankContract.Presenter {
    private AddBindBankContract.View view;
    private WalletApi mWalletApi;
    private Context context;
    private static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";
    private Map<String,Integer> map = new HashMap<>();

    public AddBindBankPresenter(AddBindBankContract.View view, Context context){
        this.view = view;
        this.context = context;
        this.mWalletApi = WalletApiImpl.getInstance(context);
        pushBankImg();
    }

    @Override
    public void getBankList() {
        view.showLoading();
        mWalletApi.queryBankList(MyToken.getMyToken(context), new ResponseCallBack<BankList>() {
            @Override
            public void callBack(ApiResponse<BankList> response) {
                view.hideLoading();
                if(response.isTokenOut()) {
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    BankList bankList = response.getData();
                    for (Bank bank:bankList.getBankList()) {
                        bank.setBankUrl(bankList.getBank_baseUrl()+bank.getBankCode()+".jpg");
                    }
                    view.showBankList(response.getData());
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });
    }

    @Override
    public void getCode(String custName,String custId,String bankCard,String bankCode,String bankName,String mobile) {

        if(TextUtils.isEmpty(custName)) {
            view.showToast("输入姓名");
            return;
        }
        if(TextUtils.isEmpty(custId)) {
            view.showToast("输入身份证号码");
            return;
        }
        if (!custId.matches(REGEX_ID_CARD)){
            view.showToast("请正确输入身份证号码");
            return;
        }
        if(TextUtils.isEmpty(bankCard)) {
            view.showToast("输入银行卡号");
            return;
        }
        if(TextUtils.isEmpty(bankCode)) {
            view.showToast("请选择银行");
            return;
        }
        if(TextUtils.isEmpty(bankName)) {
            view.showToast("请选择银行");
            return;
        }
        if(TextUtils.isEmpty(mobile)) {
            view.showToast("输入手机号码");
            return;
        }


        view.showLoading();
        mWalletApi.bindBankCode(MyToken.getMyToken(context), custName, custId, bankCard, bankCode, bankName, mobile, new ResponseCallBack<String>() {
            @Override
            public void callBack(ApiResponse<String> response) {
                view.hideLoading();
                if(response.isTokenOut()) {
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    view.sendSuccess();
                    view.showToast(response.getMsg());
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });
    }

    @Override
    public void addBank(String custName, String custId, String bankCard, String bankCode, String bankName, String mobile, String smsCode) {
        if(TextUtils.isEmpty(custName)) {
            view.showToast("输入姓名");
            return;
        }
        if(TextUtils.isEmpty(custId)) {
            view.showToast("输入身份证号码");
            return;
        }
        if (!custId.matches(REGEX_ID_CARD)){
            view.showToast("请正确输入身份证号码");
            return;
        }
        if(TextUtils.isEmpty(bankCard)) {
            view.showToast("输入银行卡号");
            return;
        }
        if(TextUtils.isEmpty(bankCode)) {
            view.showToast("请选择银行");
            return;
        }
        if(TextUtils.isEmpty(bankName)) {
            view.showToast("请选择银行");
            return;
        }
        if(TextUtils.isEmpty(mobile)) {
            view.showToast("输入手机号码");
            return;
        }
        if(TextUtils.isEmpty(smsCode)) {
            view.showToast("输入验证码");
            return;
        }

        view.showLoading();
        mWalletApi.addBindBank(MyToken.getMyToken(context), custName, custId, bankCard, bankCode, bankName, mobile, smsCode, new ResponseCallBack<AddBankResult>() {
            @Override
            public void callBack(ApiResponse<AddBankResult> response) {
                view.hideLoading();
                if(response.isTokenOut()) {
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    view.addBankSuccess();
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });
    }

    @Override
    public void getBankImg() {
        view.getBankImgSuccess(map);
    }

    private void pushBankImg(){
        map.put("工商银行", R.mipmap.bind_bank_gongshang);
        map.put("农业银行",R.mipmap.bind_bank_nongye);
        map.put("中国银行",R.mipmap.bind_bank_zhongguo);
        map.put("建设银行",R.mipmap.bind_bank_jianshe);
        map.put("交通银行",R.mipmap.bind_bank_jiaotong);
        map.put("招商银行",R.mipmap.bind_bank_zhaoshang);
        map.put("中信银行",R.mipmap.bind_bank_zhongxin);
        map.put("平安银行",R.mipmap.bind_bank_pingan);
        map.put("兴业银行",R.mipmap.bind_bank_xingye);
        map.put("浦发银行",R.mipmap.bind_bank_pufa);
        map.put("光大银行",R.mipmap.bind_bank_guangda);
        map.put("民生银行",R.mipmap.bind_bank_minsheng);
        map.put("邮政储蓄银行",R.mipmap.bind_bank_youzheng);
        map.put("北京银行",R.mipmap.bind_bank_beijing);
        map.put("上海银行",R.mipmap.bind_bank_shanghai);
    }
}
