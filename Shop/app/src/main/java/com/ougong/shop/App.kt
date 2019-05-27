package com.ougong.shop

import android.app.Application
import android.content.Context
import com.tencent.bugly.crashreport.CrashReport

class App : Application(){


    override fun onCreate() {
        super.onCreate()
        app = this
        AccountHelper.init(this)
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return
//        }
//        LeakCanary.install(this)
        CrashReport.initCrashReport(getApplicationContext(), "dbf8ed07e0", false)
    }



    companion object {
        var app : Context? = null
    }
}