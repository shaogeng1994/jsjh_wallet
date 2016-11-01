package com.jsojs.mywalletmodule.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.api.ResponseCallBack;
import com.jsojs.mywalletmodule.api.WalletApi;
import com.jsojs.mywalletmodule.api.WalletApiImpl;
import com.jsojs.mywalletmodule.bean.BindBankList;
import com.jsojs.mywalletmodule.bean.SerialNo;
import com.jsojs.mywalletmodule.contract.WithdrawContract;
import com.jsojs.mywalletmodule.modle.ApiResponse;
import com.jsojs.mywalletmodule.util.MyToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 16-10-27.
 */

public class WithdrawPresenter implements WithdrawContract.Presenter {
    private Context context;
    private WithdrawContract.View view;
    private WalletApi mWalletApi;
    private Map<String,Integer> map = new HashMap<>();

    public WithdrawPresenter(Context context, WithdrawContract.View view) {
        this.context = context;
        this.view = view;
        mWalletApi = WalletApiImpl.getInstance(context);
        pushBankImg();
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

    @Override
    public void getBankImg(String bankName) {
        if(map.get(bankName)!=null){
            view.getBankImgSuccess(map.get(bankName));
        }else {
            view.getBankImgSuccess(0);
        }
    }

    private void pushBankImg(){
        map.put("工商银行", R.mipmap.bank_icon_gongshang);
        map.put("农业银行",R.mipmap.bank_icon_nongye);
        map.put("中国银行",R.mipmap.bank_icon_zhongguo);
        map.put("建设银行",R.mipmap.bank_icon_jianshe);
        map.put("交通银行",R.mipmap.bank_icon_jiaotong);
        map.put("招商银行",R.mipmap.bank_icon_zhaoshang);
        map.put("中信银行",R.mipmap.bank_icon_zhongxin);
        map.put("平安银行",R.mipmap.bank_icon_pingan);
        map.put("兴业银行",R.mipmap.bank_icon_xinye);
        map.put("浦发银行",R.mipmap.bank_icon_pufa);
        map.put("光大银行",R.mipmap.bank_icon_guangda);
        map.put("民生银行",R.mipmap.bank_icon_minsheng);
        map.put("邮政储蓄银行",R.mipmap.bank_icon_youzheng);
        map.put("北京银行",R.mipmap.bank_icon_beijing);
        map.put("上海银行",R.mipmap.bank_icon_shanghai);
        map.put("江苏银行",R.mipmap.bank_icon_jiangsu);
        map.put("深圳农村商业银行",R.mipmap.bank_icon_sznongshang);
        map.put("杭州银行",R.mipmap.bank_icon_hangzhou);
        map.put("广东农村信用社",R.mipmap.bank_icon_gdnongxin);
        map.put("渤海银行",R.mipmap.bank_icon_bohai);
        map.put("广发银行",R.mipmap.bank_icon_guangfa);
        map.put("徽商银行",R.mipmap.bank_icon_huishang);
        map.put("上海农商行",R.mipmap.bank_icon_shanghai);
        map.put("北京农商行",R.mipmap.bank_icon_bjnongshang);
        map.put("重庆银行",R.mipmap.bank_icon_chongqin);
        map.put("华夏银行",R.mipmap.bank_icon_huaxia);
    }
}
