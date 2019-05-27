package com.ougong.shop.ActiivtyV2.brandtab.BrandTab

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.ActiivtyV2.brandtab.BrandContract
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class BrandTabPresenter : ApiPresenter<BrandTabContract.View>(), BrandTabContract.Presenter {

    override fun getStyleList(id: Int?) {
        fetch(ServiceFactory.instance.productApiservice!!.getStyleList(id)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.getStyleSucess(it)
                //              ToastUtils.toast(App.app, "获取成功")
            }
            view?.hideLoading()
        }

    }

    override fun getBrandItemList(brandId: Int?, currentIndex: Int, StyleId: Int?) {

        fetch(ServiceFactory.instance.productApiservice!!.getBrandItemList(brandId,10,currentIndex,StyleId)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.getBrandItemSucess(it.data)
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