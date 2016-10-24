package com.jsojs.mywalletmodule.api;

import com.jsojs.mywalletmodule.bean.BindBankList;
import com.jsojs.mywalletmodule.bean.WalletMsg;

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
    public final static String SOURCE = "1";

    /**
     * 操作系统
     * 1 IOS
     * 2 ANDROID
     */
    public final static String SYSVERSION = "2";

    /**
     * 版本号
     */
    public final static String APPVERSION = "1.0.4";

    /**
     * 查询钱包
     */
    public final static String QUERY_WALLET = "queryWallet";

    /**
     * 查询绑定银行卡
     */
    public final static String QUERY_BIND_BANK_LIST = "queryBindBankList";


    /**
     * @param token token
     * @param responeCallBack 回调
     */
    public void queryWallet(String token, ResponeCallBack<WalletMsg> responeCallBack);

    /**
     * @param token token
     * @param responeCallBack 回调
     */
    public void queryBindBankList(String token, ResponeCallBack<BindBankList> responeCallBack);

}
