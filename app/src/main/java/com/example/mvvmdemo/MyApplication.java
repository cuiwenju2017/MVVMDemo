package com.example.mvvmdemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.baselibrary.utils.LeoUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.mmkv.MMKV;

/**
 * Created by leo
 * on 2019/10/15.
 */
public class MyApplication extends Application {

    private static MyApplication context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        LeoUtils.initContext(this);
        //初始化MMKV
        MMKV.initialize(this);

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((mContext, layout) -> {
            return new ClassicsHeader(mContext).setPrimaryColorId(R.color.white).setAccentColorId(R.color.status_background);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((mContext, layout) -> {
            return new ClassicsFooter(mContext).setPrimaryColorId(R.color.white).setAccentColorId(R.color.status_background);
        });
    }

    public static Context getContext() {
        return context;
    }
}
