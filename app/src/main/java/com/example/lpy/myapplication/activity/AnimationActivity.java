package com.example.lpy.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.lpy.myapplication.R;
import com.example.lpy.myapplication.custom.Rotate3DAnimation;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * 动画
 */
public class AnimationActivity extends AppCompatActivity {
    private ImageView animationImg;
    private ListView listView;


    private List<String> data1 = new ArrayList<>();

    private Rotate3DAnimation rotate3DAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        for (int i = 0; i < 30; i++) {
            data1.add("动画开始..." + i);
        }
        animationImg = (ImageView) findViewById(R.id.animationImg);
        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data1);
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animationset);
//        animationImg.startAnimation(animation);

//        rotate3DAnimation = new Rotate3DAnimation(0.0f, 60000.0f, 300.0f, 1000.0f, 300.0f, true);
//        rotate3DAnimation.setDuration(10000);
//        rotate3DAnimation.setFillAfter(true);
//        rotate3DAnimation.setRepeatCount(3);
//        animationImg.startAnimation(rotate3DAnimation);
        /**********************************/
//        ObjectAnimator.ofFloat(animationImg,"translationY",-300).setDuration(3000).start();
//        ValueAnimator colorAnim = ObjectAnimator.ofInt(animationImg,"backgroundColor",/*Red*/0xFFFF8080,/*Blue*/0xFF8080FF);
//        colorAnim.setDuration(3000);
//        colorAnim.setEvaluator(new ArgbEvaluator());
//        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
//        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
//        colorAnim.start();
        /**********************************/
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(animationImg, "rotationX", 0, 360),
                ObjectAnimator.ofFloat(animationImg, "rotationY", 0, 180),
                ObjectAnimator.ofFloat(animationImg, "rotation", 0, -90),
                ObjectAnimator.ofFloat(animationImg, "translationX", 0, 90),
                ObjectAnimator.ofFloat(animationImg, "translationY", 0, 90),
                ObjectAnimator.ofFloat(animationImg, "scaleX", 1, 1.5f),
                ObjectAnimator.ofFloat(animationImg, "scaleY", 1, 0.5f),
                ObjectAnimator.ofFloat(animationImg, "alpha", 1, 0.25f, 1)
        );
        set.setStartDelay(2000);
        set.setDuration(5000).start();
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
        View listItem = listAdapter.getView(0, null, listView);
        listItem.measure(0, 0);
        int totalHeight = listItem.getMeasuredHeight() * 5 + listItem.getMeasuredHeight() / 2;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * 4);
        listView.setLayoutParams(params);
    }

}
