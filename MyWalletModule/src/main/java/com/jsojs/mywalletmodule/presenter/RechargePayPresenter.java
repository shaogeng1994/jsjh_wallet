package com.jsojs.mywalletmodule.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.jsojs.mywalletmodule.R;
import com.jsojs.mywalletmodule.api.ResponseCallBack;
import com.jsojs.mywalletmodule.api.WalletApi;
import com.jsojs.mywalletmodule.api.WalletApiImpl;
import com.jsojs.mywalletmodule.bean.DateTime;
import com.jsojs.mywalletmodule.bean.MyRayResult;
import com.jsojs.mywalletmodule.bean.Payment;
import com.jsojs.mywalletmodule.bean.RechargeOrder;
import com.jsojs.mywalletmodule.contract.RechargePayContract;
import com.jsojs.mywalletmodule.modle.ApiResponse;
import com.jsojs.mywalletmodule.util.MyToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 16-10-26.
 */

public class RechargePayPresenter implements RechargePayContract.Presenter {
    private Context context;
    private RechargePayContract.View view;
    private WalletApi mWalletApi;
    private int check = -1;
    private Map<String,Integer> map = new HashMap<>();

    public RechargePayPresenter(Context context, RechargePayContract.View view) {
        this.context = context;
        this.view = view;
        mWalletApi = WalletApiImpl.getInstance(context);
        pushBankImg();
    }

    @Override
    public void getCode(String orderNumber, String bindId, String bankCode) {
        if(TextUtils.isEmpty(orderNumber)) {
            view.showToast("找不到订单号");
            return;
        }
        if(TextUtils.isEmpty(bindId)) {
            view.showToast("选择银行卡");
            return;
        }
        if(TextUtils.isEmpty(bankCode)) {
            view.showToast("选择银行卡");
            return;
        }
        view.showLoading();
        mWalletApi.rechargePabCode(MyToken.getMyToken(context), orderNumber, bindId, bankCode, new ResponseCallBack<DateTime>() {
            @Override
            public void callBack(ApiResponse<DateTime> response) {
                view.hideLoading();
                if(response.isTokenOut()) {
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    view.getCodeSuccess(response.getData().getDateTime());
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });
    }

    @Override
    public void mode(int i) {
        check = i;
    }

    @Override
    public void checkPayMode(int i) {
        switch (i){
            case 1:
                view.doQuickPay();
                return;
            case 2:
                view.doOnlinePay();
                return;
            case 3:
                view.doOnlinePay();
                return;
        }
    }

    @Override
    public void quickPay(String orderNumber, String bindId, String dateTime, String phoneCode) {
        if(TextUtils.isEmpty(orderNumber)) {
            view.showToast("找不到订单");
            return;
        }
        if(TextUtils.isEmpty(bindId)) {
            view.showToast("选择银行卡");
            return;
        }
        if(TextUtils.isEmpty(dateTime)) {
            view.showToast("请先获取验证码");
            return;
        }
        if(TextUtils.isEmpty(phoneCode)) {
            view.showToast("输入验证码");
            return;
        }
        view.showLoading();
        mWalletApi.rechargePabPay(MyToken.getMyToken(context), orderNumber, bindId, dateTime, phoneCode, new ResponseCallBack() {
            @Override
            public void callBack(ApiResponse response) {
                view.hideLoading();
                if(response.isTokenOut()) {
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    view.quickPaySuccess();
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });
    }

    @Override
    public void onlinePay(String orderNumber, String plantBankId) {
        if(TextUtils.isEmpty(orderNumber)){
            view.showToast("找不到订单号");
            return;
        }
        if(TextUtils.isEmpty(plantBankId)){
            view.showToast("请选择绑定银行卡");
            return;
        }
        view.showLoading();
        mWalletApi.rechargeToPabPay(MyToken.getMyToken(context), orderNumber, plantBankId, new ResponseCallBack<String>() {
            @Override
            public void callBack(ApiResponse<String> response) {
                view.hideLoading();
                if(response.isTokenOut()) {
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    view.payOnlineSuccess(response.getData());
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });
    }

    @Override
    public void PayOrder(String orderId) {
//        if(TextUtils.isEmpty(orderId)){
//            view.showToast("找不到订单号");
//            return;
//        }
//        view.showLoading();
//        mWalletApi.rechargePay(MyToken.getMyToken(context), orderId, new ResponseCallBack<RechargeOrder>() {
//            @Override
//            public void callBack(ApiResponse<RechargeOrder> response) {
//                view.hideLoading();
//                if(response.isTokenOut()) {
//                    view.doTokenOut();
//                    return;
//                }
//                if(response.isSuccess()) {
//                    if(check == 1) {
//                        if(response.getData().getParinfo()==null||response.getData().getParinfo().size()==0){
//                            view.showToast("没有绑定银行卡，请用储蓄卡或信用卡支付");
//                            view.noBindBank();
//                            return;
//                        }
//                        view.selectBindBank(response.getData().getParinfo().get(0));
//                    }else if(check == 2) {
//                        view.selectBindBank(response.getData().getQuick_payment().get(0));
//                    }else {
//                        view.selectBindBank(response.getData().getQuick_payment().get(1));
//                    }
//                    view.getOrderSuccess(response.getData());
//                }else {
//                    view.showToast(response.getMsg());
//                    view.getOrderFailure();
//                }
//            }
//        });
    }

    @Override
    public void getBankImg() {
        view.getBankImgSuccess(map);
    }

    @Override
    public void getPaymentInfo(String paymentSn) {
        if(TextUtils.isEmpty(paymentSn)) {
            view.showToast("找不到支付单号");
            view.getPaymentInfoFailure();
            return;
        }
        view.showLoading();
        mWalletApi.queryPaymentInfo(MyToken.getMyToken(context), paymentSn, new ResponseCallBack<Payment>() {
            @Override
            public void callBack(ApiResponse<Payment> response) {
                view.hideLoading();
                if(response.isTokenOut()) {
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    if(response.getInfo().getAllowpaymode().get(getModeFlag())!=null) {
                        if(check == 1) {
                            if(response.getInfo().getPabkjbindcardlist()==null||response.getInfo().getPabkjbindcardlist().size()==0){
                                view.showToast("没有绑定银行卡，请用储蓄卡或信用卡支付");
                                view.noBindBank();
                                return;
                            }
                            view.selectBindBank(response.getInfo().getBankList().get(0));
                        }else if(check == 2) {
                            view.selectBindBank(response.getInfo().getBankList().get(0));
                        }else {
                            view.selectBindBank(response.getInfo().getBankList().get(1));
                        }
                        view.getPaymentInfoSuccess(response.getInfo());
                    }else {
                        switch (getModeFlag()) {
                            case "2":
                                view.showToast("不支持快捷支付");
                                break;
                            case "3":
                                view.showToast("不支持储蓄卡支付");
                                break;
                            case "4":
                                view.showToast("不支持信用卡支付");
                                break;
                        }
                        view.getPaymentInfoFailure();
                    }

                }else {
                    view.showToast(response.getMsg());
                    view.getPaymentInfoFailure();
                }
            }
        });
    }

    @Override
    public void debitCardPay(String paymentSn, String plantBankId) {
        if(TextUtils.isEmpty(paymentSn)){
            view.showToast("找不到订单");
            return;
        }
        if(TextUtils.isEmpty(plantBankId)){
            view.showToast("请选择银行");
            return;
        }

        Map<String,String> map = new HashMap<>();
        map.put("plantBankId",plantBankId);
        view.showLoading();
        mWalletApi.doPay(MyToken.getMyToken(context), paymentSn, "3", map, new ResponseCallBack<MyRayResult>() {
            @Override
            public void callBack(ApiResponse<MyRayResult> response) {
                view.hideLoading();
                if(response.isTokenOut()) {
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    view.payOnlineSuccess(response.getData().getPayhtml());
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });
    }

    @Override
    public void creditCardPay(String paymentSn, String plantBankId) {
        if(TextUtils.isEmpty(paymentSn)){
            view.showToast("找不到订单");
            return;
        }
        if(TextUtils.isEmpty(plantBankId)){
            view.showToast("请选择银行");
            return;
        }

        Map<String,String> map = new HashMap<>();
        map.put("plantBankId",plantBankId);
        view.showLoading();
        mWalletApi.doPay(MyToken.getMyToken(context), paymentSn, "4", map, new ResponseCallBack<MyRayResult>() {
            @Override
            public void callBack(ApiResponse<MyRayResult> response) {
                view.hideLoading();
                if(response.isTokenOut()) {
                    view.doTokenOut();
                    return;
                }
                if(response.isSuccess()) {
                    view.payOnlineSuccess(response.getData().getPayhtml());
                }else {
                    view.showToast(response.getMsg());
                }
            }
        });
    }

    private String getModeFlag() {
        switch (check) {
            case 1:
                return "2";
            case 2:
                return "3";
            case 3:
                return "4";
        }
        return "0";
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
        map.put("中国邮政储蓄银行",R.mipmap.bank_icon_youzheng);
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
