package com.example.lpy.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lpy.myapplication.R;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class SpeechRecognitionActivity extends Activity {

    private Button btn_click;
    private EditText result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_recognition);

        btn_click = (Button) findViewById(R.id.btn_click);
        result = (EditText) findViewById(R.id.result);

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "= 5acddccd");
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecognizerDialog dialog = new RecognizerDialog(SpeechRecognitionActivity.this, null);

                dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");

                dialog.setParameter(SpeechConstant.ACCENT, "mandarin");

                dialog.setListener(new RecognizerDialogListener() {

                    @Override

                    public void onResult(RecognizerResult recognizerResult, boolean b) {

                        printResult(recognizerResult);

                    }

                    @Override

                    public void onError(SpeechError speechError) {
                        if (speechError.getErrorCode() == 10118) {
                            Toast.makeText(SpeechRecognitionActivity.this, "没有听到语音信息", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

                dialog.show();

                Toast.makeText(SpeechRecognitionActivity.this, "请开始说话", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //回调结果：

    private void printResult(RecognizerResult results) {

        String text = parseIatResult(results.getResultString());

        // 自动填写地址

        result.append(text);

    }

    public static String parseIatResult(String json) {

        StringBuffer ret = new StringBuffer();

        try {

            JSONTokener tokener = new JSONTokener(json);

            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");

            for (int i = 0; i < words.length(); i++) {

                // 转写结果词，默认使用第一个结果

                JSONArray items = words.getJSONObject(i).getJSONArray("cw");

                JSONObject obj = items.getJSONObject(0);

                ret.append(obj.getString("w"));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return ret.toString();

    }
}
