package com.easygo.application;

import android.app.Application;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

import io.rong.imkit.RongIM;


public class MyApplication extends Application{
    private String url = "http://10.201.1.145:8080/EasyGo/appservlet";
    //private String url = "http://10.201.1.167:8080/EasyGo/appservlet";
   //Application的onCreate早于所有的Activity的onCreate方法
    @Override
    public void onCreate() {
        super.onCreate();
        /*x.Ext.init(this);
        // 是否输出debug日志, 开启debug会影响性能.
        x.Ext.setDebug(BuildConfig.DEBUG);*/
        NoHttp.init(this);
//        x.Ext.init(this);
//        // 是否输出debug日志, 开启debug会影响性能.
//        x.Ext.setDebug(BuildConfig.DEBUG);
        Logger.setDebug(true);// 开始NoHttp调试模式, 这样就能看到请求过程和日志
        RongIM.init(this);
    }

    public String getUrl() {
        return url;
    }
}
