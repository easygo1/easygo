package com.easygo.application;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by zziaguan on 2016/5/10.
 */
public class MyApplication extends Application {
    private final String host="http://10.40.7.31:8080/EasyGo/easygoservlet?";
//    private final String Servlet="";

    /*private final String login="json/user";
    private final String register="json/user";
    private final String getAll="json/user";*/

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }

//    public String getServlet() {
//        return Servlet;
//    }

    public String getHost() {
        return host;
    }

 /*   public String getLogin() {
        return host+login;
    }

    public String getRegister() {
        return host+register;
    }

    public String getGetAll() {
        return host+getAll;
    }*/
}
