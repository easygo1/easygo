package com.easygo.application;

import android.app.Application;

import com.yolanda.nohttp.NoHttp;

import org.xutils.BuildConfig;
import org.xutils.x;


public class MyApplication extends Application{
    private String url = "http://10.201.1.173:8080/EasyGo/appservlet";
   //Application的onCreate早于所有的Activity的onCreate方法
    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.init(this);
        x.Ext.init(this);
        // 是否输出debug日志, 开启debug会影响性能.
        x.Ext.setDebug(BuildConfig.DEBUG);
    }

    public String getUrl() {
        return url;
    }
}
