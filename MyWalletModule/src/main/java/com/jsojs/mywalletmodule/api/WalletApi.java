package com.jsojs.mywalletmodule.api;

import com.jsojs.mywalletmodule.bean.AddBankResult;
import com.jsojs.mywalletmodule.bean.BankList;
import com.jsojs.mywalletmodule.bean.BindBankList;
import com.jsojs.mywalletmodule.bean.DateTime;
import com.jsojs.mywalletmodule.bean.MyRayResult;
import com.jsojs.mywalletmodule.bean.Payment;
import com.jsojs.mywalletmodule.bean.RechargeOrder;
import com.jsojs.mywalletmodule.bean.SerialNo;
import com.jsojs.mywalletmodule.bean.WalletMsg;

import java.util.Map;

/**
 * Created by root on 16-10-24.
 */

public interface WalletApi {

    /**
     * 来源
     * 1 B2B
     * 2 独立
     * 3 酒商云
     */
    String SOURCE = "1";

    /**
     * 操作系统
     * 1 IOS
     * 2 ANDROID
     */
    String SYSVERSION = "2";

    /**
     * 版本号
     */
    String APPVERSION = "1.0.4";

    /**
     * 查询钱包
     */
    String QUERY_WALLET = "queryWallet";

    /**
     * 查询绑定银行卡
     */
    String QUERY_BIND_BANK_LIST = "queryBindBankList";

    /**
     * 查询银行信息
     */
    String QUERY_BANK_LIST = "queryBankList";

    /**
     * 绑定银行验证码获取
     */
    String BIND_BANK_CODE = "bindBankCode";

    /**
     * 添加绑定银行卡
     */
    String ADD_BIND_BANK = "addBingBank";
    /**
     *移除绑定银行卡
     */
    String DEL_BIND_BANK = "delBindBank";
    /**
     * 提现短信验证码
     */
    String WITHDRAWALS_CODE = "withdrawalsCode";
    /**
     * 提现
     */
    String WITHDRAWALS = "withdrawals";
    /**
     * 充值订单创建
     */
    String RECHARGE_ADD = "rechargeAdd";

    /**
     * 查询充值订单
     */
    String RECHARGE_PAY = "rechargePay";
    /**
     * 快捷支付验证码获取
     */
    String RECHARGE_PAB_CODE = "rechargePabCode";
    /**
     * 快捷支付付款
     */
    String RECHARGE_PAB_PAY = "rechargePabPay";
    /**
     * 在线支付跳转
     */
    String RECHARGE_TO_PAB_PAY = "rechargeToPabPay";

    /**
     * 查询收银台
     */
    String QUERY_PAYMENT_INFO = "queryPaymentInfo";

    /**
     * 1.0.5后的支付借口
     */
    String DO_PAY = "dopay";


    /**
     * @param token token
     * @param responseCallBack   回调监听
     */
    void queryWallet(String token, ResponseCallBack<WalletMsg> responseCallBack);

    /**
     * @param token token
     * @param responseCallBack   回调监听
     */
    void queryBindBankList(String token, ResponseCallBack<BindBankList> responseCallBack);

    /**
     * @param token token
     * @param responseCallBack   回调监听
     */
    void queryBankList(String token, ResponseCallBack<BankList> responseCallBack);

    /**
     * @param token token
     * @param custName  开户姓名
     * @param custId    身份证号码
     * @param bankCard  银行卡号
     * @param bankCode  超级银号
     * @param bankName  开户银行
     * @param mobile    手机号码
     * @param responseCallBack   回调监听
     */
    void bindBankCode(String token,String custName,String custId,String bankCard,String bankCode,String bankName,String mobile,ResponseCallBack<String> responseCallBack);


    /**
     * @param token token
     * @param custName  开户姓名
     * @param custId    身份证号码
     * @param bankCard  银行卡号
     * @param bankCode  超级银号
     * @param bankName  开户银行
     * @param mobile    手机号码
     * @param smsCode   验证码
     * @param responseCallBack  回调监听
     */
    void addBindBank(String token, String custName, String custId, String bankCard, String bankCode, String bankName, String mobile, String smsCode, ResponseCallBack<AddBankResult> responseCallBack);

    /**
     * @param token token
     * @param id    绑定ID
     * @param responseCallBack   回调监听
     */
    void delBindBank(String token,String id,ResponseCallBack responseCallBack);

    /**
     * @param token token
     * @param cardId    银行卡ID
     * @param amount    提现金额
     * @param responseCallBack   回调监听
     */
    void withdrawalsCode(String token,String cardId,String amount,ResponseCallBack<SerialNo> responseCallBack);

    /**
     * @param token token
     * @param cardId    银行卡ID
     * @param serialNo  短信指令号
     * @param smsCode   验证码
     * @param responseCallBack   回调监听
     */
    void withdrawals(String token,String cardId,String serialNo,String smsCode,ResponseCallBack responseCallBack);

    /**
     * @param token token
     * @param amount    金额
     * @param responseCallBack   回调监听
     */
    void rechargeAdd(String token, String amount, ResponseCallBack<Map<String,String>> responseCallBack);

    /**
     * @param token token
     * @param orderId   订单号
     * @param responseCallBack  回调监听
     */
    void rechargePay(String token,String orderId,ResponseCallBack<RechargeOrder> responseCallBack);

    /**
     * @param token token
     * @param orderNumber   订单号
     * @param bindId    绑定卡号ID
     * @param bankCode  银行卡代码
     * @param responseCallBack   回调监听
     */
    void rechargePabCode(String token, String orderNumber, String bindId, String bankCode, ResponseCallBack<DateTime> responseCallBack);

    /**
     * @param token token
     * @param orderNumber   订单号
     * @param bindId    绑定卡号ID
     * @param dateTime
     * @param phoneCode 验证码
     * @param responseCallBack   回调监听
     */
    void rechargePabPay(String token,String orderNumber,String bindId,String dateTime,String phoneCode,ResponseCallBack responseCallBack);

    /**
     * @param token token
     * @param orderNumber   订单号
     * @param plantBankId   银行卡代码
     * @param responseCallBack   回调监听
     */
    void rechargeToPabPay(String token,String orderNumber,String plantBankId,ResponseCallBack<String> responseCallBack);


    /**
     * @param token token
     * @param paymentSn     支付单号
     * @param responseCallBack  回调监听
     */
    void queryPaymentInfo(String token,String paymentSn,ResponseCallBack<Payment> responseCallBack);

    /**
     * @param token token
     * @param paymentSn 支付单号
     * @param payType   支付类型
     * @param payMap    更多参数
     * @param responseCallBack  回调监听
     */
    void doPay(String token,String paymentSn,String payType,Map<String,String> payMap,ResponseCallBack<MyRayResult> responseCallBack);

}
