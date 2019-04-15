package com.example.lpy.myapplication.func.sorts;

import android.util.Log;

import java.util.Arrays;

/**
 * 冒泡泡排序
 */
public class BubbleSort {

    /**
     * 从前往后比
     *
     * @param a
     */
    public static void bubbleSort(int[] a) {
        int temp;
        boolean flag;
        for (int i = 0; i < a.length - 1; i++) {
            flag = true;
            for (int j = 0; j < a.length - 1 - i; j++) { //从第一个开始比，每次比完最大的放最后
                if (a[j] > a[j + 1]) {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    flag = false;
                }
            }
            if (flag) {
                Log.e("bubbleSort111===", "排了<" + i + ">次===" + Arrays.toString(a));
                break;
            }
        }
    }

    public static void bubbleSort2(int[] a) {
        int temp;
        boolean flag;
        for (int i = 0; i < a.length - 1; i++) {
            flag = true;
            for (int j = a.length - 1; j > i; j--) {
                if (a[j] < a[j - 1]) {
                    temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;
                    flag = false;
                }
            }
            if (flag) {
                Log.e("bubbleSort222===", "排了<" + i + ">次===" + Arrays.toString(a));
                break;
            }
        }
    }
}
