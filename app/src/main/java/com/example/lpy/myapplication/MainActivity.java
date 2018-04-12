package com.example.lpy.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lpy.myapplication.activity.AnimationActivity;
import com.example.lpy.myapplication.activity.BarrageVideoActivity;
import com.example.lpy.myapplication.activity.ConstraintActivity;
import com.example.lpy.myapplication.activity.FreeDragActivity;
import com.example.lpy.myapplication.activity.JsActivity;
import com.example.lpy.myapplication.activity.MagicActivity;
import com.example.lpy.myapplication.activity.MyEditTextActivity;
import com.example.lpy.myapplication.activity.MyFreeStyleActivity;
import com.example.lpy.myapplication.activity.SlidingConflict;
import com.example.lpy.myapplication.activity.SpeechRecognitionActivity;
import com.example.lpy.myapplication.adapter.ListViewAdapter;

import java.util.Arrays;

import rx.Observable;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private String[] listString = {"自定义光能魔法阵动画", "JS交互", "自定义EditText", "ConstraintLayout",
            "FreeDragView", "Sliding Conflict", "自启", "动画", "弹幕", "自定义View练习", "讯飞语音识别"};
    private ListViewAdapter adapter;

    /**
     * 排序数据
     */
    private int[] array = {1000000000, 666666, 88888888, 1, 999999999, 333, 7777777, 4444, 55555, 22};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ListViewAdapter(this, listString);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) goActivity(MagicActivity.class);
                else if (position == 1) goActivity(JsActivity.class);
                else if (position == 2) goActivity(MyEditTextActivity.class);
                else if (position == 3) goActivity(ConstraintActivity.class);
                else if (position == 4) goActivity(FreeDragActivity.class);
                else if (position == 5) goActivity(SlidingConflict.class);
                else if (position == 6) goActivity(MainActivity.class);
                else if (position == 7) goActivity(AnimationActivity.class);
                else if (position == 8) goActivity(BarrageVideoActivity.class);
                else if (position == 9) goActivity(MyFreeStyleActivity.class);
                else if (position == 10) goActivity(SpeechRecognitionActivity.class);
            }
        });
        someTest();
    }

    private void someTest() {

        /**1.测试getIntent()默认是否为空**/
//        Intent intent = getIntent();
//        Log.e("Intent===", intent.toString());

        /**2.RxJava小测试**/
//        rxJavaTest();

        /**3.冒泡排序**/
//        bubbleSort();

        /**4.选择排序**/
//        selectionSort();

        /**5.插入排序**/
        insertSort();
    }

    /**
     * 5.插入排序
     * 平均时间复杂度:O(n2)
     * 在要排序的一组数中，假定前n-1个数已经排好序，现在将第n个数插到前面的有序数列中，使得这n个数也是排好顺序的。
     * 如此反复循环，直到全部排好顺序。
     * （例如：玩扑克牌，手按大小排序安插扑克）
     */
    private void insertSort() {
        int temp;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                } else {
                    break;
                }
            }
        }
        Log.e("插入排序===", Arrays.toString(array));
    }

    /**
     * 4.选择排序
     * 平均时间复杂度:O(n2)
     * 过程:第一次遍历N-1（第一个数除外）个数，找到所有数里最小的，跟第一位数交换位置；
     * 第二次遍历N-2(第一二个数除外)个数，找到N-1个数里最小的和第二位数交换位置；
     * ...
     * 第N-1次遍历1个数，如果它比第N-1个数小，就交换位置，否则不动。
     */
    private void selectionSort() {

        for (int i = 0; i < array.length - 1; i++) { //总共需要遍历length-1次
            int minIndex = i; //标记最小数值的位置，默认第几次遍历，最小值就是第几位
            for (int j = i + 1; j < array.length; j++) { //从第i+1个数开始比较到最后一个数
                if (array[j] < array[minIndex]) { //如果第j位数比之前标记的最小数小
                    minIndex = j; //则最小数标记为第j位
                }
            }
            if (minIndex != i) { //如果先前默认的最小数标记位有变化
                int temp = array[i]; //记录当前位置数值
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }
        Log.e("选择排序===", Arrays.toString(array));
    }

    /**
     * 3.冒泡排序
     * 平均时间复杂度:O(n2)
     * 过程:从后向前两两比较，较大的放后面，第一次比完之后，最小数会被换到最前面；
     * 重复该过程......
     */
    private void bubbleSort() {
        int middleNum;
        boolean flag; //停止遍历的标志
        for (int i = 0; i < array.length - 1; i++) { //总共需要遍历length-1次
            Log.e("i===", i + "");
            flag = false;
            for (int j = array.length - 1; j > i; j--) { //从后往前比较
                if (array[j] < array[j - 1]) { //如果后者比前者小，则交换位置
                    middleNum = array[j];  //存储后一位数值
                    array[j] = array[j - 1]; //将前一位的数值赋值给后一位
                    array[j - 1] = middleNum; //将刚才储存的后一位的数值赋值给前一位
                    flag = true; //表示有变动，则继续遍历
                }
            }
            if (!flag) break; //表示该轮遍历无任何变动，说明所有的数已经排序完成，则停止之后的无用遍历。
        }
        Log.e("bubbleSort===", Arrays.toString(array));
    }

    /**
     * RxJava小测试
     */
    private void rxJavaTest() {
        Observable.from(listString).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("==RxJava=", s);
            }
        });
    }

    private void goActivity(Class activity) {
        startActivity(new Intent(this, activity));
        overridePendingTransition(R.anim.animation_item, R.anim.animation_item);
    }

}
