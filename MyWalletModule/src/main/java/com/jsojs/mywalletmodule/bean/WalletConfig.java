package com.jsojs.mywalletmodule.bean;

/**
 * Created by root on 16-10-25.
 */

public class WalletConfig {
    private String url;
    private String appversion;
    private String source;
    private String sysversion;

    public WalletConfig() {
    }

    public WalletConfig(String url, String appversion, String source) {
        this.url = url;
        this.appversion = appversion;
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSysversion() {
        return sysversion;
    }

    public void setSysversion(String sysversion) {
        this.sysversion = sysversion;
    }
}
