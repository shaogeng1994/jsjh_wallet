package com.jsojs.mywalletmodule.app;

import android.app.Dialog;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jsojs.mywalletmodule.R;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by Administrator on 2016/7/21.
 */
public class WebViewActivity extends BaseActivity {
    private WebView webView;
    private String html;
    private Dialog dialog;
    private ActionBar actionBar;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_webview);
        initView();
        simpleJsClick();

    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setTitle("支付页面");
        initView();
    }

    @Override
    protected void onBack() {
        finish();
    }

    private void initView(){
        progressBar = (ProgressBar) findViewById(R.id.webview_activity_progressbar);
    }


    public void simpleJsClick() {
        WebView webView = (WebView) findViewById(R.id.webview_activity_webview);
        html = getIntent().getStringExtra("html");
        Log.i("shao",html);



        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
// 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
//扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
//自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.addJavascriptInterface(new JsInteration(),"jsojsJSinterface");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == progressBar.getVisibility()) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
            webView.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();  // 接受所有网站的证书
//                dialog.cancel();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String wohleJS = "var title = $('#status');" +
                        "title.bind('DOMNodeInserted',function(e) {" +
                        "if($('#status').text().split('交易成功').length>0){" +
                        "alert('交易成功，确定关闭');window.jsojsJSinterface.onSumResult(1);}" +
                        "});";
                view.loadUrl("javascript:function myFunction(){"+wohleJS+"}");
                view.loadUrl("javascript:myFunction()");
            }
        });

        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8",null);



    }


    public class  JsInteration {

        @JavascriptInterface
        public void onSumResult(int result) {
            Log.i("shao", "onSumResult result=" + result);
            if(result==1){
                Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_LONG).show();
                setResult(RESULT_OK);
                finish();
            }
        }
    }


    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("shao", "===>>> shouldOverrideUrlLoading method is called!");
            // TODO Auto-generated method stub
            URL local_url;
            URLConnection connection;
            try {
                local_url = new URL(url);
                connection = local_url.openConnection();
                connection.setConnectTimeout(15000);
                connection.connect();
            } catch (Exception e) {
            }

            final HttpGet httpGet = new HttpGet(url);

            Thread theard = new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        HttpResponse response;
                        String htmlContent;
                        HttpClient httpClient = new DefaultHttpClient();
                        response = httpClient.execute(httpGet);
                        if (response.getStatusLine().getStatusCode() == 200) {
                            Header[] headers = response.getAllHeaders();
                            for (Header header : headers) {
                                String name = header.getName();
                                String value = header.getValue();
                                Log.i("shao", "===>>> name:" + name);
                                Log.i("shao", "===>>> value:" + value);
                            }
                            HttpEntity entity = response.getEntity();
                            if (entity != null) {
                                InputStream inputStream = entity.getContent();
                                htmlContent = convertToString(inputStream);
                                Log.i("shao", "===>>> htmlContent:" + htmlContent);
                                webView.loadData(htmlContent, "text/html", "utf-8");
                            }
                        }
                    } catch (Exception e) {
                    }
                    ;
                }
            });
            theard.start();
            //view.loadData(htmlContent, "text/html", "utf-8");
            return true;
            //return super.shouldOverrideUrlLoading(view, url);
        }
        public String convertToString(InputStream inputStream) {
            StringBuffer string = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    string.append(line + "\n");
                }
            } catch (IOException e) {
            }
            return string.toString();
        }

    }





}
