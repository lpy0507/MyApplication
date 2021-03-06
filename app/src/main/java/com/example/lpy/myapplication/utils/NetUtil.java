package com.example.lpy.myapplication.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * 网络相关工具类
 */
public class NetUtil {

    public static String getWifiInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = null;
        if (wifiManager != null)
            info = wifiManager.getConnectionInfo();
        if (info == null) return "Error";
        return info.getIpAddress() + "";
    }
}
