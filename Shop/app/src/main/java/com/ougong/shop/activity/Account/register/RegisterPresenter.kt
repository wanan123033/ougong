package com.ougong.shop.activity.Account.register

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.R
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory
import io.armcha.ribble.domain.fetcher.result_listener.RequestType
import java.net.PasswordAuthentication

class RegisterPresenter: ApiPresenter<RegisterContract.View>(), RegisterContract.Presenter {
    override fun CheckPhoneNo(smsCode: String) {
        fetch(ServiceFactory.instance.accountAPIService!!.CheckPhoneNo(smsCode))  {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
//                ToastUtils.toast(App.app, App.app!!.getString(R.string.send_sms_sucess))
                view?.CheckPhoneSucess()
            }
            view?.hideLoading()
        }
    }

    override fun sendCheckNo(phoneNumber: String) {
        fetch(ServiceFactory.instance.accountAPIService!!.sendSms(phoneNumber , 0), RequestType.SENDSMS)  {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                ToastUtils.toast(App.app, App.app!!.getString(R.string.send_sms_sucess))

                view?.checkNOSucess()
            }
            view?.hideLoading()

        }
    }

    override fun register(passwordAuthentication: PasswordAuthentication, smsCode: String) {
        fetch(
            ServiceFactory.instance.accountAPIService!!.registerGeneral(
                String(passwordAuthentication.password),
                passwordAuthentication.userName,
            smsCode),RequestType.REGISTE
        ) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.RegisterSucess()
            }
            view?.hideLoading()

        }
    }


    override fun onRequestError(errorMessage: String?) {
        view?.apply {
            hideLoading()
            showError(errorMessage)
        }
    }

    //因为有两种请求，这里就需要区分一下。
    override fun onRequestStart(requestType: RequestType) {
        super.onRequestStart(requestType)
//        view?.showLoading()
        view?.apply{
        }
    }


}