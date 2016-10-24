package com.jsojs.mywalletmodule.util;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by root on 16-8-23.
 */
public class MyJson {
    public static String getString(JSONObject jsonObject,String s){
        if(jsonObject.has(s)){
            try {
                return jsonObject.getString(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static JSONObject getObject(JSONObject jsonObject,String s){
        if(jsonObject.has(s)){
            try {
                return jsonObject.getJSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new JSONObject();
    }

    public static JSONArray getArray(JSONObject jsonObject, String s){
        if(jsonObject.has(s)){
            try {
                return jsonObject.getJSONArray(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new JSONArray();
    }

    public static String getCode(JSONObject jsonObject){
        if(jsonObject.has("code")){
            try {
                return jsonObject.getString("code");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static boolean isOK(JSONObject jsonObject){
        if(getCode(jsonObject).equals("1"))return true;
        return false;
    }

    public static void getMsg(Context context,JSONObject jsonObject){
        if(jsonObject.has("msg")){
            try {
                Toast.makeText(context,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
