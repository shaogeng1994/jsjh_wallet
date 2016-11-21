package com.jsojs.mywalletmodule.api;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jsojs.mywalletmodule.bean.AddBankResult;
import com.jsojs.mywalletmodule.bean.BankList;
import com.jsojs.mywalletmodule.bean.BindBankList;
import com.jsojs.mywalletmodule.bean.DateTime;
import com.jsojs.mywalletmodule.bean.MyRayResult;
import com.jsojs.mywalletmodule.bean.Payment;
import com.jsojs.mywalletmodule.bean.RechargeOrder;
import com.jsojs.mywalletmodule.bean.SerialNo;
import com.jsojs.mywalletmodule.bean.WalletConfig;
import com.jsojs.mywalletmodule.bean.WalletMsg;
import com.jsojs.mywalletmodule.modle.ApiResponse;
import com.jsojs.mywalletmodule.util.MyLog;
import com.jsojs.mywalletmodule.util.WalletManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 16-10-24.
 */

public class WalletApiImpl implements WalletApi {

    public final static int REQUEST_METHOD = Request.Method.POST;

    private static WalletApi instance;

    private static String URL;

    private Context context;

    private RequestQueue queue;

    private WalletManager mWalletManager;

    private WalletConfig mWalletConfig;

    private WalletApiImpl(Context context) {
        if(queue == null){
            this.context = context.getApplicationContext();
            queue = Volley.newRequestQueue(context);
            mWalletManager = WalletManager.getInstance();
            mWalletConfig = mWalletManager.getWalletConfig();
            URL = mWalletConfig.getUrl();
        }
    }

    public static WalletApi getInstance(Context context) {
        if(instance==null){
            instance = new WalletApiImpl(context);
        }
        return instance;
    }


    @Override
    public void queryWallet(String token, final ResponseCallBack<WalletMsg> responseCallBack) {
        final HashMap<String,String> map = getCommonMap();
        map.put("action",QUERY_WALLET);
        map.put("token",token);
        StringRequest request = new StringRequest(REQUEST_METHOD,URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ApiResponse<WalletMsg> response = JSON.parseObject(s,new TypeReference<ApiResponse<WalletMsg>>(){});
                responseCallBack.callBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getRequestError(error,responseCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);
        MyLog.showLog("api",QUERY_WALLET);

    }

    @Override
    public void queryBindBankList(String token, final ResponseCallBack<BindBankList> responseCallBack) {
        final HashMap<String,String> map = getCommonMap();
        map.put("action",QUERY_BIND_BANK_LIST);
        map.put("token",token);
        StringRequest request = new StringRequest(REQUEST_METHOD, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ApiResponse<BindBankList> response = JSON.parseObject(s,new TypeReference<ApiResponse<BindBankList>>(){});
                responseCallBack.callBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getRequestError(error,responseCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);
        MyLog.showLog("api",QUERY_BIND_BANK_LIST);
    }

    @Override
    public void queryBankList(String token, final ResponseCallBack<BankList> responseCallBack) {
        final HashMap<String,String> map = getCommonMap();
        map.put("action",QUERY_BANK_LIST);
        map.put("token",token);
        StringRequest request = new StringRequest(REQUEST_METHOD, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ApiResponse<BankList> response = JSON.parseObject(s,new TypeReference<ApiResponse<BankList>>(){});
                responseCallBack.callBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getRequestError(error,responseCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);
        MyLog.showLog("api",QUERY_BANK_LIST);
    }

    @Override
    public void bindBankCode(String token, String custName, String custId, String bankCard, String bankCode, String bankName, String mobile, final ResponseCallBack<String> responseCallBack) {
        final HashMap<String,String> map = getCommonMap();
        map.put("action",BIND_BANK_CODE);
        map.put("token",token);
        map.put("custName",custName);
        map.put("custId",custId);
        map.put("bankCard",bankCard);
        map.put("bankCode",bankCode);
        map.put("bankName",bankName);
        map.put("mobile",mobile);
        StringRequest request = new StringRequest(REQUEST_METHOD, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ApiResponse<String> response = JSON.parseObject(s,new TypeReference<ApiResponse<String>>(){});
                responseCallBack.callBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getRequestError(error,responseCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);
        MyLog.showLog("api",BIND_BANK_CODE);
    }

    @Override
    public void addBindBank(String token, String custName, String custId, String bankCard, String bankCode, String bankName, String mobile, String smsCode, final ResponseCallBack<AddBankResult> responseCallBack) {
        final HashMap<String,String> map = getCommonMap();
        map.put("action",ADD_BIND_BANK);
        map.put("token",token);
        map.put("custName",custName);
        map.put("custId",custId);
        map.put("bankCard",bankCard);
        map.put("bankCode",bankCode);
        map.put("bankName",bankName);
        map.put("mobile",mobile);
        map.put("smsCode",smsCode);
        StringRequest request = new StringRequest(REQUEST_METHOD, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ApiResponse<AddBankResult> response = JSON.parseObject(s,new TypeReference<ApiResponse<AddBankResult>>(){});
                responseCallBack.callBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getRequestError(error,responseCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);
        MyLog.showLog("api",ADD_BIND_BANK);
    }

    @Override
    public void delBindBank(String token, String id, final ResponseCallBack responseCallBack) {
        final HashMap<String,String> map = getCommonMap();
        map.put("action",DEL_BIND_BANK);
        map.put("token",token);
        map.put("id",id);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ApiResponse<String> response = JSON.parseObject(s,new TypeReference<ApiResponse<String>>(){});
                responseCallBack.callBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                getRequestError(volleyError,responseCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);
        MyLog.showLog("api",DEL_BIND_BANK);
    }

    @Override
    public void withdrawalsCode(String token, String cardId, String amount, final ResponseCallBack<SerialNo> responseCallBack) {

        final HashMap<String,String> map = getCommonMap();
        map.put("action",WITHDRAWALS_CODE);
        map.put("token",token);
        map.put("cardId",cardId);
        map.put("amount",amount);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ApiResponse<SerialNo> response = JSON.parseObject(s,new TypeReference<ApiResponse<SerialNo>>(){});
//                JSONObject jsonObject;
//                try {
//                    jsonObject = new JSONObject(s);
//                    response.setData(jsonObject.getString("serialNo"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                responseCallBack.callBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                getRequestError(volleyError,responseCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);
        MyLog.showLog("api",WITHDRAWALS_CODE);
    }

    @Override
    public void withdrawals(String token, String cardId, String serialNo, String smsCode, final ResponseCallBack responseCallBack) {

        final HashMap<String,String> map = getCommonMap();
        map.put("action",WITHDRAWALS);
        map.put("token",token);
        map.put("cardId",cardId);
        map.put("serialNo",serialNo);
        map.put("smsCode",smsCode);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ApiResponse<String> response= JSON.parseObject(s,new TypeReference<ApiResponse<String>>(){});
                responseCallBack.callBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                getRequestError(volleyError,responseCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);
        MyLog.showLog("api",WITHDRAWALS);
    }

    @Override
    public void rechargeAdd(String token, String amount, final ResponseCallBack<Map<String,String>> responseCallBack) {

        final HashMap<String,String> map = getCommonMap();
        map.put("action",RECHARGE_ADD);
        map.put("token",token);
        map.put("amount",amount);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ApiResponse<Map<String,String>> response = JSON.parseObject(s,new TypeReference<ApiResponse<Map<String,String>>>(){});
                responseCallBack.callBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                getRequestError(volleyError,responseCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);
        MyLog.showLog("api",RECHARGE_ADD);
    }

    @Override
    public void rechargePay(String token, String orderId, final ResponseCallBack<RechargeOrder> responseCallBack) {
        final HashMap<String,String> map = getCommonMap();
        map.put("action",RECHARGE_PAY);
        map.put("token",token);
        map.put("orderId",orderId);
        StringRequest request = new StringRequest(REQUEST_METHOD, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ApiResponse<RechargeOrder> response = JSON.parseObject(s,new TypeReference<ApiResponse<RechargeOrder>>(){});
                responseCallBack.callBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getRequestError(error,responseCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);
        MyLog.showLog("api",RECHARGE_PAY);
    }

    @Override
    public void rechargePabCode(String token, String orderNumber, String bindId, String bankCode, final ResponseCallBack<DateTime> responseCallBack) {

        final HashMap<String,String> map = getCommonMap();
        map.put("action",RECHARGE_PAB_CODE);
        map.put("token",token);
        map.put("orderNumber",orderNumber);
        map.put("bindId",bindId);
        map.put("bankCode",bankCode);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ApiResponse<DateTime> response = JSON.parseObject(s,new TypeReference<ApiResponse<DateTime>>(){});
                responseCallBack.callBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                getRequestError(volleyError,responseCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);
        MyLog.showLog("api",RECHARGE_PAB_CODE);
    }

    @Override
    public void rechargePabPay(String token, String orderNumber, String bindId, String dateTime, String phoneCode, final ResponseCallBack responseCallBack) {

        final HashMap<String,String> map = getCommonMap();
        map.put("action",RECHARGE_PAB_PAY);
        map.put("token",token);
        map.put("ordernumber",orderNumber);
        map.put("bindId",bindId);
        map.put("dateTime",dateTime);
        map.put("phone_code",phoneCode);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ApiResponse<String> response = JSON.parseObject(s,new TypeReference<ApiResponse<String>>(){});
                responseCallBack.callBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                getRequestError(volleyError,responseCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);
        MyLog.showLog("api",RECHARGE_PAB_PAY);
    }

    @Override
    public void rechargeToPabPay(String token, String orderNumber, String plantBankId, final ResponseCallBack<String> responseCallBack) {
        final HashMap<String,String> map = getCommonMap();
        map.put("action",RECHARGE_TO_PAB_PAY);
        map.put("token",token);
        map.put("orderNumber",orderNumber);
        map.put("plantBankId",plantBankId);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ApiResponse<String> response = new ApiResponse<>();
                if(s.startsWith("{")&&s.endsWith("}")){
                    response = JSON.parseObject(s,new TypeReference<ApiResponse<String>>(){});
                }else {
                    response.setCode("1");
                    response.setData(s);
                }
                responseCallBack.callBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                getRequestError(volleyError,responseCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);
        MyLog.showLog("api",RECHARGE_TO_PAB_PAY);
    }

    @Override
    public void queryPaymentInfo(String token, String paymentSn, final ResponseCallBack<Payment> responseCallBack) {
        final HashMap<String,String> map = getCommonMap();
        map.put("action",QUERY_PAYMENT_INFO);
        map.put("token",token);
        map.put("payment_sn",paymentSn);
        StringRequest request = new StringRequest(REQUEST_METHOD, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ApiResponse<Payment> response = JSON.parseObject(s,new TypeReference<ApiResponse<Payment>>(){});
                responseCallBack.callBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getRequestError(error,responseCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);
        MyLog.showLog("api",QUERY_PAYMENT_INFO);
    }

    @Override
    public void doPay(String token, String paymentSn, final String payType, Map<String, String> payMap, final ResponseCallBack<MyRayResult> responseCallBack) {
        final HashMap<String,String> map = getCommonMap();
        map.put("action",DO_PAY);
        map.put("token",token);
        map.put("payment_sn",paymentSn);
        map.put("paytype",payType);
        map.putAll(payMap);
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ApiResponse<MyRayResult> response;
                if(payType.equals("3")||payType.equals("4")||payType.equals("5")){
                    response = JSON.parseObject(s,new TypeReference<ApiResponse<MyRayResult>>(){});
                }else {
                    ApiResponse<String> response2 = JSON.parseObject(s,new TypeReference<ApiResponse<String>>(){});
                    response = new ApiResponse<>();
                    response.setCode(response2.getCode());
                    response.setMsg(response2.getMsg());
                }
                responseCallBack.callBack(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                getRequestError(volleyError,responseCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);
        MyLog.showLog("api",DO_PAY);
    }


    private HashMap getCommonMap() {
        HashMap<String,String> commonMap = new HashMap<>();
        commonMap.put("source",mWalletConfig.getSource());
        commonMap.put("appversion",mWalletConfig.getAppversion());
        commonMap.put("sysversion",SYSVERSION);
        if(mWalletConfig.getOtherMap()!=null);
            commonMap.putAll(mWalletConfig.getOtherMap());
        return commonMap;
    }

    public void getRequestError(VolleyError volleyError, ResponseCallBack responeCallBack){
        ApiResponse respone= new ApiResponse();
        if(volleyError.networkResponse!=null){
            switch (volleyError.networkResponse.statusCode){
                case 504:
                    respone.setMsg("请求超时");
                    break;
                case 404:
                    respone.setMsg("找不到服务器");
                    break;
                case 500:
                    respone.setMsg("服务器未响应");
                    break;
            }
        }
        Log.i("shao","volleyError:"+volleyError.toString());
        if(volleyError.getMessage()!=null){
            Log.i("shao",volleyError.getMessage());
            if(volleyError.getMessage().startsWith("java.net.ConnectException")){
                respone.setMsg("连接失败");
            }else if(volleyError.getMessage().startsWith("java.net.UnknownHostException")){
                respone.setMsg("找不到服务器");
            }else{
                respone.setMsg("发生异常");
            }
        }
        responeCallBack.callBack(respone);
    }

    public void addToRequest(Request request){
        request.setRetryPolicy(new DefaultRetryPolicy(50000, 1, 1.0f));
        queue.add(request);
    }
}
