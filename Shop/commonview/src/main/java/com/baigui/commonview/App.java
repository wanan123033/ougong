package com.baigui.commonview;

import android.support.multidex.MultiDexApplication;

/**
 * Created by chenjnshan on 2016/7/5.
 */
public class App extends MultiDexApplication {
    public boolean isSlidingClose = true;//是否测滑关闭activity

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
