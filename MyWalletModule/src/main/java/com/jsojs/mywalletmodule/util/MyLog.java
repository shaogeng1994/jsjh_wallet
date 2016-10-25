package com.jsojs.mywalletmodule.util;

import android.util.Log;

/**
 * Created by root on 16-9-30.
 */
public class MyLog {
    public static boolean isDebug = true;
    public static void showLog(String msg){
        showLog("",msg);
    }
    public static void showLog(String name,String msg){
        if(isDebug){
            if(!name.equals("")){
                Log.i("shao",name+"-->"+msg);
            }else{
                Log.i("shao",msg);
            }
        }
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setIsDebug(boolean isDebug) {
        MyLog.isDebug = isDebug;
    }
}
