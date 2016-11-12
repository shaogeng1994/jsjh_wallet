package com.jsojs.wallet.app;

import android.app.Application;

import com.jsojs.mywalletmodule.*;
import com.jsojs.mywalletmodule.bean.WalletConfig;
import com.jsojs.mywalletmodule.util.WalletManager;

/**
 * Created by root on 16-10-25.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        WalletManager.getInstance().setWalletConfig(new WalletConfig("http://app.jiushangjiuhui.com:803/app/","1.0.5","1"));
    }
}
