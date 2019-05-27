package com.ougong.shop.base_mvp.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.baigui.commonview.R
import com.ougong.shop.ConstString
import com.ougong.shop.activity.Account.login.LogInActivity
import io.armcha.arch.BaseMVPFragment
import io.armcha.ribble.presentation.base_mvp.base.BaseContract

/**
 * Created by Chatikyan on 01.08.2017.
 */
abstract class BaseFragment<V : BaseContract.View, P : BaseContract.Presenter<V>> : BaseMVPFragment<V, P>(),View.OnClickListener {
//
//    @Inject
//    lateinit var navigator: Navigator
//
//    protected lateinit var activityComponent: ActivityComponent
    protected lateinit var activity: BaseActivity<*, *>
//
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.activity = context
        }
    }

    override fun GotoLogin() {
//        activity.start<LogInActivity>()

        activity.startActivityForResult(Intent(activity, LogInActivity::class.java),ConstString.LOG_IN_REQUEST)
    }

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
        super.onViewCreated(view, savedInstanceState)
        StarusBarUtils.setStatusBarColor(activity!!, R.color.f5f5f5)
        StarusBarUtils.setAndroidNativeLightStatusBar(activity, true)
        initData()
        initView()
    }

}