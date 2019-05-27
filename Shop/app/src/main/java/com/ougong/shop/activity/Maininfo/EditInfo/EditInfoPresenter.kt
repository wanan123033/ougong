package com.ougong.shop.activity.Maininfo.EditInfo

import android.text.TextUtils
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.AccountHelper
import com.ougong.shop.App
import com.ougong.shop.Bean.User
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class EditInfoPresenter : ApiPresenter<EditInfoContract.View>(), EditInfoContract.Presenter {

    override fun uploadImage(path: String) {

        val file = File(path)

        val requestBody = RequestBody.create(MediaType.parse("application/otcet-stream"), file)
        val body = MultipartBody.Part.createFormData("upfile", file.getName(), requestBody)

        view?.showLoading()
        fetch(ServiceFactory.instance.userInfoApiService!!.uploadImage(body)) {
            if (!TextUtils.equals(it.state, "SUCCESS")) {
                ToastUtils.toast(App.app, "错误")
            } else {

                LogUtils.e(it.url!!,this)
                view?.uploadPicSicess(it.url!!)
            }
            view?.hideLoading()

        }
    }

    override fun updataInfo(user: User) {

        fetch(ServiceFactory.instance.userInfoApiService!!.updataUserInfo(user.getMap())) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                AccountHelper.synchronizationUser()
                view?.updataSucess()
            }
            view?.hideLoading()
        }
    }

    override fun onRequestError(errorMessage: String?) {
        super.onRequestError(errorMessage)
        ToastUtils.toast(App.app, errorMessage)
        view?.hideLoading()
    }

    class upLoaddata {
        var state: String? = null
        var url: String? = null
    }
}