package com.easygo.application;

import android.app.Application;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

import io.rong.imkit.RongIM;


public class MyApplication extends Application{
    private String url = "http://10.201.1.173:8080/EasyGo/appservlet";
    //private String url = "http://10.201.1.167:8080/EasyGo/appservlet";
//    private String url = "http://10.201.1.159:8080/EasyGo/appservlet";


   //Application的onCreate早于所有的Activity的onCreate方法
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化nohttp
        NoHttp.init(this);
        Logger.setDebug(true);// 开始NoHttp调试模式, 这样就能看到请求过程和日志
        RongIM.init(this);
    }

    public String getUrl() {
        return url;
    }

}
