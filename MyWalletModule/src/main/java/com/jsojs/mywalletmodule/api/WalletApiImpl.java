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
import com.jsojs.mywalletmodule.app.APIUrl;
import com.jsojs.mywalletmodule.bean.BindBankList;
import com.jsojs.mywalletmodule.bean.WalletMsg;
import com.jsojs.mywalletmodule.modle.Respone;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 16-10-24.
 */

public class WalletApiImpl implements WalletApi {

    public final static int REQUEST_METHOD = Request.Method.POST;

    private static WalletApi instance;

    private Context context;

    private RequestQueue queue;

    private WalletApiImpl(Context context) {
        if(queue == null){
            this.context = context;
            queue = Volley.newRequestQueue(context);
        }
    }

    public static WalletApi getInstance(Context context) {
        if(instance==null){
            instance = new WalletApiImpl(context);
        }
        return instance;
    }


    @Override
    public void queryWallet(String token, final ResponeCallBack<WalletMsg> responeCallBack) {
        final HashMap<String,String> map = getCommonMap();
        map.put("action",QUERY_WALLET);
        map.put("token",token);
        StringRequest request = new StringRequest(REQUEST_METHOD, APIUrl.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Respone<WalletMsg> respone = JSON.parseObject(response,new TypeReference<Respone<WalletMsg>>(){});
                responeCallBack.callBack(respone);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getRequestError(error,responeCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);

    }

    @Override
    public void queryBindBankList(String token, final ResponeCallBack<BindBankList> responeCallBack) {
        final HashMap<String,String> map = getCommonMap();
        map.put("action",QUERY_BIND_BANK_LIST);
        map.put("token",token);
        StringRequest request = new StringRequest(REQUEST_METHOD, APIUrl.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Respone<BindBankList> respone = JSON.parseObject(response,new TypeReference<Respone<BindBankList>>(){});
                responeCallBack.callBack(respone);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getRequestError(error,responeCallBack);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        addToRequest(request);
    }


    private HashMap getCommonMap() {
        HashMap<String,String> commonMap = new HashMap<>();
        commonMap.put("source",SOURCE);
        commonMap.put("appversion",APPVERSION);
        commonMap.put("sysversion",SYSVERSION);
        return commonMap;
    }

    public void getRequestError(VolleyError volleyError, ResponeCallBack responeCallBack){
        Respone respone= new Respone();
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
