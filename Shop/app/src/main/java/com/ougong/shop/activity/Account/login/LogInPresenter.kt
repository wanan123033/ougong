package com.ougong.shop.activity.Account.login

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory
import io.armcha.ribble.domain.fetcher.result_listener.RequestType
import java.net.PasswordAuthentication
import javax.inject.Inject

class LogInPresenter @Inject constructor()
: ApiPresenter<LogInContract.View>(), LogInContract.Presenter {

    override fun RefeshInfo() {
        fetch(ServiceFactory.instance.accountAPIService!!.RefeshUserInfo()){
            if (it.status != 200){
                if(!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            }else{
                view?.refeshUserInfosucess(it.data)
            }
            view?.hideLoading()

        }
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    fun onStart() {
        //这种迷人的设计，真蛋疼。
//        if (AUTH statusIs SUCCESS)
//            view?.showLoading()
    }


    override fun makeLogin(passwordAuthentication: PasswordAuthentication) {

        view?.showLoading()
        fetch(ServiceFactory.instance.accountAPIService!!.LogIn(String(passwordAuthentication.password),passwordAuthentication.userName),AUTH){
            if (it.status != 200){
                if(!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            }else{
                view?.loginSucess(it.data)
            }
            view?.hideLoading()

        }
    }

    override fun checkLogin() {

    }


    override fun onRequestError(errorMessage: String?) {
        view?.apply {
            hideLoading()
            showError(errorMessage)
        }
        ToastUtils.toast(App.app,errorMessage)
    }

    override fun onRequestStart() {
//        view?.showLoading()
        view?.apply{
        }
    }


}