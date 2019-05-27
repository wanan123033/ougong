package com.baigui.commonview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baigui.commonlib.kotlinUtils.StarusBarUtils.setAndroidNativeLightStatusBar
import com.baigui.commonlib.kotlinUtils.StarusBarUtils.setStatusBarColor

abstract class BaseFrgment  : Fragment() , View.OnClickListener {
    abstract fun setContentViewSource():Int

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(setContentViewSource(),container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setStatusBarColor(activity!!,R.color.white)
        setAndroidNativeLightStatusBar(activity,true)
        initData()
        initView()
    }
}