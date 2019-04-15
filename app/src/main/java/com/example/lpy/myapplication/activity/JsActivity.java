package com.example.lpy.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lpy.myapplication.R;

/**
 * JS交互
 */
public class JsActivity extends Activity {

    private TextView text;
    private WebView webView;
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js);
        text = (TextView) findViewById(R.id.text);
        edit = (EditText) findViewById(R.id.edit);
        webView = (WebView) findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl("file:///android_asset/js.html");
        webView.addJavascriptInterface(new JsToJava(), "AndroidWebView");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                // 这里拿到参数就可以根据约定好的格式解析了
                text.setText("prompt方式，参数：" + message);
                // 调用一下cancel或者confirm都行，就是模拟手动点击了确定或者取消按钮
                result.cancel();
                return true;
            }
        });
    }

    // 原生的方式
    private class JsToJava {
        // 高版本需要加这个注解才能生效
        @JavascriptInterface
        public void jsMethod(final String paramFromJS) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("传统方式js调用java，参数：" + paramFromJS);
                }
            });
        }

        //在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public void showInfoFromJs(final String name, final String phone, final String card) {
            //必须运行在主线程上，否则不起作用
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String msg = "姓名：" + name + "\n" + "手机号：" + phone + "\n" + "卡号：" + card;
                    text.setText(msg.replace("\\n", "\n"));
                    startActivity(new Intent(JsActivity.this, MagicActivity.class));
                }
            });
        }

    }

    public void sendInfoToJs(View view) {
        String msg = edit.getText().toString();
        //调用js中的函数：showInfoFromJava(msg)
        webView.loadUrl("javascript:showInfoFromJava('" + msg + "')");
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            destoryWebView(webView);
        }
        super.onDestroy();
    }

    private void destoryWebView(WebView webview) {
        webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        //清除网页访问留下的缓存,由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
        webview.clearCache(true);
        //清除当前webview访问的历史记录,只会webview访问历史记录里的所有记录除了当前访问记录
        webview.clearHistory();
        ((ViewGroup) webview.getParent()).removeView(webview);
        webview.removeAllViews();
        webview.destroy();
    }
}
