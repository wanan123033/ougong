package com.baigui.commonview

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.AttributeSet
import android.view.View

/**
 * 这个Base我实在不了解我改如何封装，这里暂时不管任何逻辑，只是吧必要的函数都声明一下，可以避免在oncreat中出现大量的必然调用的函数
 */
abstract class BaseActivity<T, U> : FragmentActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(setContentViewSource())
        initData()
        initView()
        getLifecycle()
    }

    /**
     * 主要用来设置初始化的view的内容，然后设置必要的监听函数
     */
    open fun initView() {
    }

    open fun initData() {
    }

    /**
     * 必然会出现的东东，
     */
    override fun onClick(v: View?) {

    }

    abstract fun setContentViewSource():Int
}