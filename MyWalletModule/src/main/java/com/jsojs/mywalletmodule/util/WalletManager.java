package com.jsojs.mywalletmodule.util;

import com.jsojs.mywalletmodule.BuildConfig;
import com.jsojs.mywalletmodule.bean.WalletConfig;


/**
 * Created by root on 16-10-25.
 */

public class WalletManager {
    private static WalletManager instance;
    private boolean isDebug;
    private WalletConfig walletConfig;

    private WalletManager(){
        this.isDebug = false;
        this.walletConfig = new WalletConfig(BuildConfig.URL,"1.0.4","1");
    }

    public static WalletManager getInstance(){
        if(instance==null)
            instance = new WalletManager();
        return instance;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public WalletConfig getWalletConfig() {
        return walletConfig;
    }

    public void setWalletConfig(WalletConfig walletConfig) {
        this.walletConfig = walletConfig;
    }
}
