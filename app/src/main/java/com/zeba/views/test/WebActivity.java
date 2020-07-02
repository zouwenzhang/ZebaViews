package com.zeba.views.test;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class WebActivity extends AppCompatActivity {

    private SwipeRefreshLayout refreshLayout;
    public WebView webView;
    private String loadUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        refreshLayout=findViewById(R.id.refresh_layout);
        initWeb();
        initListener();
        loadUrl="https://www.lalamove.com";
        webView.loadUrl(loadUrl);
    }

    protected void initListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(loadUrl);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void initWeb() {
        webView=findViewById(R.id.web);
        webView.requestFocusFromTouch();//支持获取手势焦点，便于用户输入
        WebSettings settings = webView.getSettings();
        String dir = getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        //启用地理定位
        settings.setGeolocationEnabled(true);
        //设置定位的数据库路径
        settings.setGeolocationDatabasePath(dir);
        //start
        settings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");
        //自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //自动缩放
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        //支持获取手势焦点
        webView.requestFocusFromTouch();
        //end
        settings.setDomStorageEnabled(true);//是否开启本地DOM存储
        settings.setJavaScriptEnabled(true);//启用支持javascript
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);//优先不使用缓存
        settings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        settings.setDisplayZoomControls(false);//不显示缩放控制按钮（3.0以上有效）
        settings.setAppCacheEnabled(true);
        settings.supportMultipleWindows();
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
//        settings.setSavePassword(true);
//        settings.setSaveFormData(true);
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
        webView.setWebChromeClient(webChromeClient);
        //如果不设置WebViewClient，请求会跳转系统浏览器
        webView.setWebViewClient(new WebViewClient() {
            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    String url=request.getUrl().toString();
                    if(url.startsWith("https://rest.lalamove.com")||url.startsWith("https://app.lalamove.com")||url.startsWith("https://auth.vanapi.com")){
                        //
                        StringBuffer sb=new StringBuffer();
                        sb.append("[\r\n");
                        sb.append(request.getMethod()+"\r\n");
                        sb.append(url+"\r\n");
                        Set<Map.Entry<String,String>> ks= request.getRequestHeaders().entrySet();
                        for(Map.Entry<String,String> en:ks){
                            sb.append(en.getKey()+":"+en.getValue()+",");
                        }
                        sb.append("\r\n");
                        sb.append(request.getUrl().getQuery()+"\r\n");
                        sb.append(request.getUrl().getEncodedQuery()+"\r\n");
                        WebResourceResponse resp=super.shouldInterceptRequest(view, request);
                        if(resp!=null){
                            sb.append("r1="+resp.getEncoding()+"\r\n");
                            sb.append("r2="+resp.getMimeType()+"\r\n");
                            sb.append("r3="+resp.getReasonPhrase()+"\r\n");
                            sb.append("r4="+resp.getStatusCode()+"\r\n");
                        }
                        if(resp!=null&&resp.getData()!=null){
                            byte[] d=new byte[2048];
                            int len=0;
                            try {
                                while(true){
                                    len=resp.getData().read(d);
                                    if(len==-1){
                                        break;
                                    }
                                    byte[] rd=new byte[len];
                                    System.arraycopy(d,0,rd,0,len);
                                    sb.append(new String(rd,"utf-8"));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        sb.append("\r\n]\r\n");
                        CrashUtil.LogToText(sb.toString());
                        Log.e("zwz",sb.toString());
                        return resp;
                    }

                }
                return super.shouldInterceptRequest(view, request);
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                if(!checkSkipUrl(url)){
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
            {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if(!checkSkipUrl(request.getUrl().toString())){
                        view.loadUrl(request.getUrl().toString());
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                if(error.getPrimaryError() == android.net.http.SslError.SSL_UNTRUSTED ){
//                    if(isSSLTrusted){
                handler.proceed();
//                        return;
//                    }
//                    showSSLAuthDialog(handler);
//                }else{
//                    handler.cancel();
//                }
            }
        });
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if(newProgress>=100){
                newProgress=0;
            }
            int w=(int)(view.getMeasuredWidth()*(newProgress/100f));
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            return true;
        }
    };

    public boolean checkSkipUrl(String url){
        try{
            if (!url.startsWith("http")) {
                // 以下固定写法
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
            }else{
                loadUrl=url;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onResume() {
        webView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        webView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }

}

