package com.ougong.shop.activity.Account

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.R
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory
import io.armcha.ribble.domain.fetcher.result_listener.RequestType
import java.net.PasswordAuthentication

class AccountPresent : ApiPresenter<AccountPresentContract.View>(), AccountPresentContract.Presenter {

    override fun forgetPassword(phone: String, checknu: String, password: String) {

        fetch(ServiceFactory.instance.accountAPIService!!.passwordFind(phone, checknu)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                passwordReset(it.data, password)
            }
            view?.hideLoading()
        }

    }

    fun passwordReset(uuid: String, password: String) {
        fetch(
            ServiceFactory.instance.accountAPIService!!.passwordReset(password, password, uuid)
        ) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.Sucess()
            }
            view?.hideLoading()
        }
    }

    override fun register(passwordAuthentication: PasswordAuthentication, smsCode: String) {

    }

    override fun sendCheckNo(phoneNumber: String,type : Int) {
        fetch(
            ServiceFactory.instance.accountAPIService!!.sendSms(phoneNumber,type)
            , RequestType.SENDSMS
        ) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                ToastUtils.toast(App.app, App.app!!.getString(R.string.send_sms_sucess))
                view?.sendCheckNoSucess()
            }
            view?.hideLoading()

        }

    }

    override fun changePhone(password: String, phone: String, mscode: String) {


        fetch(
            ServiceFactory.instance.accountAPIService!!.ChangePhone(password, phone, mscode)
            , RequestType.SENDSMS
        ) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                ToastUtils.toast(App.app, "手机号更改成功")
            }
            view?.hideLoading()

        }

    }

    override fun changePassword(oldPassword: String, password: String) {

        fetch(
            ServiceFactory.instance.accountAPIService!!.PasswordModify(oldPassword, password)
            , RequestType.SENDSMS
        ) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.ChangePasswordSucess()
            }
            view?.hideLoading()

        }

    }

    override fun CheckPhoneNo(smsCode: String) {
        fetch(ServiceFactory.instance.accountAPIService!!.CheckPhoneNo(smsCode))  {
            if (it.status != 200) {

                //验证成功，系统已经有这个电话
                if (it.status == 500){
                    if (!TextUtils.isEmpty(it.message) ){
                        view?.CheckPhoneSucess(true)
                    }
                }else{
                    if (!TextUtils.isEmpty(it.message)) {
                        ToastUtils.toast(App.app, it.message)
                    }
                }

            } else {
                view?.CheckPhoneSucess(false)
//                ToastUtils.toast(App.app, App.app!!.getString(R.string.send_sms_sucess))
//                view?.CheckPhoneSucess()
            }
            view?.hideLoading()
        }
    }

    override fun onRequestStart() {
        super.onRequestStart()
    }

    override fun onRequestError(requestType: RequestType, errorMessage: String?) {
        super.onRequestError(requestType, errorMessage)
    }

}