package com.easygo.application;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;
public class MyApplication extends Application{
    private String url = "http://192.168.191.1:8080/EasyGo/easygoservlet";

   //Application的onCreate早于所有的Activity的onCreate方法
    @Override
    public void onCreate() {
        super.onCreate();
        /*x.Ext.init(this);
        // 是否输出debug日志, 开启debug会影响性能.
        x.Ext.setDebug(BuildConfig.DEBUG);*/
      //  NoHttp.init(this);
       x.Ext.init(this);
//        // 是否输出debug日志, 开启debug会影响性能.
      x.Ext.setDebug(BuildConfig.DEBUG);
     //   Logger.setDebug(true);// 开始NoHttp的调试模式, 这样就能看到请求过程和日志

    }

    public String getUrl() {
        return url;
    }
}
