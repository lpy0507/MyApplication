package com.example.lpy.myapplication.utils;

import android.util.Log;

/**
 * LPY-2017.3.27
 * Log管理
 */
public class LogUtil {

    public static boolean showLog = true;

    public static void v(String TAG, String logText) {
        if (showLog) {
            Log.v(TAG, logText);
        }
    }

    public static void d(String TAG, String logText) {
        if (showLog) {
            Log.d(TAG, logText);
        }
    }

    public static void i(String TAG, String logText) {
        if (showLog) {
            Log.i(TAG, logText);
        }
    }

    public static void w(String TAG, String logText) {
        if (showLog) {
            Log.w(TAG, logText);
        }
    }


    public static void e(String TAG, String logText) {
        if (showLog) {
            Log.e(TAG, logText);
        }
    }
}
