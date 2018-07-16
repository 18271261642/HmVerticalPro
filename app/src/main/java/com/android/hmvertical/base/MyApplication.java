package com.android.hmvertical.base;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.android.hmvertical.exection.CrashHandler;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */

public class MyApplication extends Application {

    static RequestQueue requestQueue;
    static MyApplication myApplication;

    //activity集合
    private List<AppCompatActivity> appList;
    private CrashHandler crashHandler;

    public static int urlFlagCode = 0;

    public static String userKey = null;


    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        //初始化Http的相关配置
        initHttpData();
        appList = new ArrayList<>();
        //初始化异常收集
        crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

    }

    public static int getUrlFlagCode(int code) {
        return urlFlagCode = code;
    }

    public static String getUserKey() {
        return userKey;
    }

    public static void setUserKey(String userKey) {
        MyApplication.userKey = userKey;
    }

    private void initHttpData() {
        Logger.setDebug(true);
        Logger.setTag("日志Tag");
        NoHttp.initialize(InitializationConfig.newBuilder(this)
                //设置全局连接超时时间
                .connectionTimeout(30 * 1000)
                //服务器响应时间
                .readTimeout(10 * 1000)
                //重试次数
                .retry(1)
                //底层网络配置
                .networkExecutor(new OkHttpNetworkExecutor()).build());
    }

    public static MyApplication getMyApplication(){
        return myApplication;
    }

    //添加所有Activity
    public void addActivity(AppCompatActivity activity){
        if(!appList.contains(activity)){
            appList.add(activity);
        }
    }

    //销毁所有Activity
    public void removeAllActivity(){
        for(AppCompatActivity app : appList){
            app.finish();
        }
    }

    /**
     * Volley 请求的RequestQueue
     * @return
     */
    public static RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(myApplication);
        }
        return requestQueue;
    }
}
