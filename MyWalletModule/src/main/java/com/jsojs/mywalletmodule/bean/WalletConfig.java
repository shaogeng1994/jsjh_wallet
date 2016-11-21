package com.jsojs.mywalletmodule.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by root on 16-10-25.
 */

public class WalletConfig implements Serializable {
    private String url;
    private String appversion;
    private String source;
    private String sysversion;
    private Map<String,String> otherMap;

    public WalletConfig() {
    }

    public WalletConfig(String url, String appversion, String source) {
        this.url = url;
        this.appversion = appversion;
        this.source = source;
    }

    public WalletConfig(String url, String appversion, String source,Map<String,String> otherMap) {
        this.url = url;
        this.appversion = appversion;
        this.source = source;
        this.otherMap = otherMap;
    }

    public Map<String, String> getOtherMap() {
        return otherMap;
    }

    public void setOtherMap(Map<String, String> otherMap) {
        this.otherMap = otherMap;
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
