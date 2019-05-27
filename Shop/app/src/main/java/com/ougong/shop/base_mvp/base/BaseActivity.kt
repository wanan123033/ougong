package com.ougong.shop.base_mvp.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import com.ougong.shop.ConstString
import com.ougong.shop.activity.Account.login.LogInActivity
import io.armcha.arch.BaseMVPActivity
import io.armcha.ribble.presentation.base_mvp.base.BaseContract

abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>>
    : BaseMVPActivity<V, P>() {

    @CallSuper
    override fun onDestroy() {
//        dialog?.dismiss()
        super.onDestroy()
    }

    override fun GotoLogin() {
        startActivityForResult(Intent(this, LogInActivity::class.java), ConstString.LOG_IN_REQUEST)
    }

    @CallSuper
    override fun onBackPressed() {
        super.onBackPressed()
    }

//    protected abstract fun injectDependencies()

//    private fun getAppComponent() = App.instance.applicationComponent

    inline protected fun <reified T : Fragment> goTo(keepState: Boolean = true,
                                                     withCustomAnimation: Boolean = false,
                                                     arg: Bundle = Bundle.EMPTY) {
//        navigator.goTo<T>(keepState = keepState,
//                withCustomAnimation = withCustomAnimation,
//                arg = arg)
    }

    fun showDialog(title: String, message: String, buttonText: String = "Close") {
//        dialog = MaterialDialog(this).apply {
//            message(message)
//                    .title(title)
//                    .addPositiveButton(buttonText) {
//                        hide()
//                    }
//                    .show()
//        }
    }

    fun showErrorDialog(message: String?, buttonText: String = "Close") {
//        showDialog(getString(getString(R.string.error_title), message ?: emptyString, buttonText)
    }


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

    abstract fun setContentViewSource():Int
}
