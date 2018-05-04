package com.example.lpy.myapplication.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lpy.myapplication.R;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SpeechRecognitionActivity extends Activity {

    private Button btn_click;
    private EditText result;

    private MediaPlayer mediaPlayer;
    private int playTimes = 0;
    private StringBuffer mResult = new StringBuffer();
    /** 最大等待时间， 单位ms */
    private int maxWaitTime = 500;
    /** 每次等待时间 */
    private int perWaitTime = 100;
    /** 出现异常时最多重复次数 */
    private int maxQueueTimes = 3;
    /** 音频文件 */
    private String fileName = "/mnt/sdcard/voice.wav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_recognition);
        mediaPlayer = MediaPlayer.create(this, R.raw.voice);

        btn_click = (Button) findViewById(R.id.btn_click);
        result = (EditText) findViewById(R.id.result);

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "= 5acddccd");
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                startSpeak();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (playTimes < 3) {
                    playTimes++;
                    mediaPlayer.start();
                    startSpeak();
                    Log.e("playTimes===", playTimes + "次");
                } else {
                    mediaPlayer.stop();
                }
            }
        });
    }

    private void startSpeak() {
        RecognizerDialog dialog = new RecognizerDialog(SpeechRecognitionActivity.this, null);
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        dialog.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        dialog.setParameter(SpeechConstant.ASR_AUDIO_PATH, "/mnt/sdcard/record/voice.wav");
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

    /**
     * 回调结果：
     */
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    public String voice2words(String fileName) throws InterruptedException {
        return voice2words(fileName, true);
    }

    /**
     *
     * @desc: 工具类，在应用中有一个实例即可， 但是该实例是有状态的， 因此要消除其他调用对状态的修改，所以提供一个init变量
     * @auth: zona
     * 2017年1月4日 下午4:38:45
     * @param fileName
     * @param init 是否初始化最大等待时间。
     * @return
     * @throws InterruptedException
     */
    public String voice2words(String fileName, boolean init) throws InterruptedException {
        if(init) {
            maxWaitTime = 500;
            maxQueueTimes = 3;
        }
        if(maxQueueTimes <= 0) {
            mResult.setLength(0);
            mResult.append("解析异常！");
            return mResult.toString();
        }
        this.fileName = fileName;

        return recognize();
    }


    // *************************************音频流听写*************************************

    /**
     * 听写
     * @return
     * @throws InterruptedException
     */
    private String recognize() throws InterruptedException {
        if (SpeechRecognizer.getRecognizer() == null)
            SpeechRecognizer.createRecognizer(this, (InitListener) recListener);
        return RecognizePcmfileByte();
    }

    /**
     * 自动化测试注意要点 如果直接从音频文件识别，需要模拟真实的音速，防止音频队列的堵塞
     * @throws InterruptedException
     */
    private String RecognizePcmfileByte() throws InterruptedException {
        // 1、读取音频文件
        FileInputStream fis = null;
        byte[] voiceBuffer = null;
        try {
            fis = new FileInputStream(new File(fileName));
            voiceBuffer = new byte[fis.available()];
            fis.read(voiceBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 2、音频流听写
        if (0 == voiceBuffer.length) {
            mResult.append("no audio avaible!");
        } else {
            //解析之前将存出结果置为空
            mResult.setLength(0);
            SpeechRecognizer recognizer = SpeechRecognizer.getRecognizer();
            recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
            recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            recognizer.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
            //写音频流时，文件是应用层已有的，不必再保存
//          recognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH,
//                  "./iflytek.pcm");
            recognizer.setParameter( SpeechConstant.RESULT_TYPE, "plain" );
            recognizer.startListening(recListener);
            ArrayList<byte[]> buffers = splitBuffer(voiceBuffer,
                    voiceBuffer.length, 4800);
            for (int i = 0; i < buffers.size(); i++) {
                // 每次写入msc数据4.8K,相当150ms录音数据
                recognizer.writeAudio(buffers.get(i), 0, buffers.get(i).length);
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            recognizer.stopListening();

            // 在原有的代码基础上主要添加了这个while代码等待音频解析完成，recognizer.isListening()返回true，说明解析工作还在进行
            while(recognizer.isListening()) {
                if(maxWaitTime < 0) {
                    mResult.setLength(0);
                    mResult.append("解析超时！");
                    break;
                }
                Thread.sleep(perWaitTime);
                maxWaitTime -= perWaitTime;
            }
        }
        return mResult.toString();
    }
    /**
     * 将字节缓冲区按照固定大小进行分割成数组
     *
     * @param buffer
     *            缓冲区
     * @param length
     *            缓冲区大小
     * @param spsize
     *            切割块大小
     * @return
     */
    private ArrayList<byte[]> splitBuffer(byte[] buffer, int length, int spsize) {
        ArrayList<byte[]> array = new ArrayList<byte[]>();
        if (spsize <= 0 || length <= 0 || buffer == null
                || buffer.length < length)
            return array;
        int size = 0;
        while (size < length) {
            int left = length - size;
            if (spsize < left) {
                byte[] sdata = new byte[spsize];
                System.arraycopy(buffer, size, sdata, 0, spsize);
                array.add(sdata);
                size += spsize;
            } else {
                byte[] sdata = new byte[left];
                System.arraycopy(buffer, size, sdata, 0, left);
                array.add(sdata);
                size += left;
            }
        }
        return array;
    }

    /**
     * 听写监听器
     */
    private RecognizerListener recListener = new RecognizerListener() {

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        public void onBeginOfSpeech() { }

        public void onEndOfSpeech() { }

        public void onVolumeChanged(int volume) { }

        public void onResult(RecognizerResult result, boolean islast) {
            mResult.append(result.getResultString());
        }

        public void onError(SpeechError error) {
            try {
                voice2words(fileName);
                maxQueueTimes--;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }

        public void onEvent(int eventType, int arg1, int agr2, String msg) { }

    };
}
