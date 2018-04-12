package com.example.lpy.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;

import com.example.lpy.myapplication.R;
import com.example.lpy.myapplication.custom.MyEditText;

/**
 * 自定义EditText
 */
public class MyEditTextActivity extends Activity {
    private MyEditText nameEt, cardEt, bankEt, phoneEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_edit_text);
        nameEt = (MyEditText) findViewById(R.id.nameEt);
        nameEt.setText("刘鹏远");
        nameEt.setInputType(InputType.TYPE_CLASS_TEXT);
        nameEt.setMaxLength(10);
        nameEt.setCloseBtnVisible(false);
        nameEt.setEditable(false);

        cardEt = (MyEditText) findViewById(R.id.cardEt);
        cardEt.setInputType(InputType.TYPE_CLASS_NUMBER);
        cardEt.setMaxLength(18);
        cardEt.setInputFilter("1234567890x");

        bankEt = (MyEditText) findViewById(R.id.bankEt);
        bankEt.setInputType(InputType.TYPE_CLASS_NUMBER);
        bankEt.setMaxLength(30);

        phoneEt = (MyEditText) findViewById(R.id.phoneEt);
        phoneEt.setInputType(InputType.TYPE_CLASS_NUMBER);
        phoneEt.setMaxLength(11);
    }
}
