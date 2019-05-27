package com.ougong.shop.ActiivtyV2.brandtab.BrandActivity

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class brandPresenter : ApiPresenter<BrandContract.View>(), BrandContract.Presenter {

    override fun getBrandDetail(id: Int) {

        fetch(ServiceFactory.instance.productApiservice!!.getBrandDetail(id)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.getBrandDetailSucess(it.data)
                //              ToastUtils.toast(App.app, "获取成功")
            }
            view?.hideLoading()
        }

    }

    override fun getBrandTagList(brandId: Int) {

        fetch(ServiceFactory.instance.productApiservice!!.getBrandTagList(brandId)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.getBrandTagSucess(it)
                //              ToastUtils.toast(App.app, "获取成功")
            }
            view?.hideLoading()
        }
    }


    override fun getProductList(brandId: Int?, category: Int?, page: Int, order: String?, sort: String?) {

        fetch(ServiceFactory.instance.productApiservice!!.getProduct(brandId = brandId, category = category, page = page,order = order,sort = sort)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.getProductListSucess(it)
            }
            view?.hideLoading()
        }
    }

    override fun onRequestError(errorMessage: String?) {
        super.onRequestError(errorMessage)
        ToastUtils.toast(App.app,errorMessage)
    }
}