package com.example.lpy.myapplication.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    public static void showMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
