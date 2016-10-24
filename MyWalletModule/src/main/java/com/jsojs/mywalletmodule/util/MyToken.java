package com.jsojs.mywalletmodule.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import com.jsojs.mywalletmodule.bean.UserInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/7/15.
 */
public class MyToken {
    public static String getMyToken(Context context){
        SharedPreferences mSharedPreferences = context.getSharedPreferences("InfoSharedPreferences", 0);
        String token = mSharedPreferences.getString("token", "");
        return token;
    }
    public static void clearMyToken(Context context){
        SharedPreferences mSharedPreferences = context.getSharedPreferences("InfoSharedPreferences", 0);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static UserInfo getUserInfo(Context context){
        UserInfo userInfo = new UserInfo();
        SharedPreferences mSharedPreferences = context.getSharedPreferences("InfoSharedPreferences", 0);
        userInfo.setToken(mSharedPreferences.getString("token",""));
        userInfo.setBaseGender(mSharedPreferences.getString("base_gender",""));
        userInfo.setBindEmail(mSharedPreferences.getString("bind_email",""));
        userInfo.setBindMobile(mSharedPreferences.getString("bind_mobile",""));
        userInfo.setCustName(mSharedPreferences.getString("custname",""));
        userInfo.setHead(mSharedPreferences.getString("head",""));
        userInfo.setTitle(mSharedPreferences.getString("title",""));
        userInfo.setBaseNick(mSharedPreferences.getString("base_nick",""));
        userInfo.setAccount(mSharedPreferences.getString("account",""));
        return userInfo;
    }


    public static void saveInfo(Context context,UserInfo userInfo){
        SharedPreferences mSharedPreferences = context.getSharedPreferences("InfoSharedPreferences", 0);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("token",userInfo.getToken());
        mEditor.putString("base_gender",userInfo.getBaseGender());
        mEditor.putString("bind_email",userInfo.getBindEmail());
        mEditor.putString("bind_mobile",userInfo.getBindMobile());
        mEditor.putString("custname",userInfo.getCustName());
        mEditor.putString("head",userInfo.getHead());
        mEditor.putString("title",userInfo.getTitle());
        mEditor.commit();
    }

    public static void saveBitmap(Context context,Bitmap bitmap, String bitName) throws IOException
    {
        File file = new File(context.getCacheDir()+"/"+bitName);
        Log.d("shao",file.getPath());
        if(file.exists()){
            file.delete();
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, out))
            {
                out.flush();
                out.close();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
