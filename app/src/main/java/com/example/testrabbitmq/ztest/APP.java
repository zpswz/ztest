package com.example.testrabbitmq.ztest;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by Administrator on 2017/11/9 0009.
 */

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
