package com.ougong.shop.activity.mainHomepage

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.activity.MainLabeFrgment.LabeContract
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class HomepagePresenter : ApiPresenter<MainHomePageContract.View>(), MainHomePageContract.Presenter {
    override fun getAllList() {
        fetch(ServiceFactory.instance.productApiservice!!.getCateryBean()) {

            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.getAllListSucess(it)
            }
            view?.hideLoading()

        }

    }

    override fun getProduct1(id: Int) {

        fetch(ServiceFactory.instance.productApiservice!!.getProduct(category = id)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.getProduceSucess1(it)
                //               ToastUtils.toast(App.app, "获取成功")
            }
            view?.hideLoading()

        }


    }


    override fun getProduct2(id: Int) {

        fetch(ServiceFactory.instance.productApiservice!!.getProduct(category = id)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.getProduceSucess2(it)
                //              ToastUtils.toast(App.app, "获取成功")
            }
            view?.hideLoading()

        }
    }

    override fun onRequestError(errorMessage: String?) {
        super.onRequestError(errorMessage)

        ToastUtils.toast(App.app,errorMessage)
    }
}