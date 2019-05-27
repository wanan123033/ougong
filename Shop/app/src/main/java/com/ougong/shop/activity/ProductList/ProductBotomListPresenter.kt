package com.ougong.shop.activity.ProductList

import android.text.TextUtils
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.activity.Product.ProductContract
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory


class ProductBotomListPresenter : ApiPresenter<ProductBottomContract.View>(), ProductBottomContract.Presenter {
    override fun getProduct(id: Int, page: Int, order: String, sort: String) {

        fetch(ServiceFactory.instance.productApiservice!!.getProduct(category = id, page = page,order = order,sort = sort)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.getProductSucess(it)
            }
            view?.hideLoading()

        }
    }

//    override fun getProduct(id: Int,order : String) {
//
//        fetch(ServiceFactory.instance.productApiservice!!.getProduct(category = id, limit = 16,order = order)) {
//            if (it.status != 200) {
//                if (!TextUtils.isEmpty(it.message)) {
//                    ToastUtils.toast(App.app, it.message)
//                }
//            } else {
//                view.getProductSucess(it)
//                ToastUtils.toast(App.app, "获取成功")
//            }
//            view?.hideLoading()
//
//        }
//
//
//    }


    override fun onRequestError(errorMessage: String?) {
        super.onRequestError(errorMessage)
        ToastUtils.toast(App.app, errorMessage)
    }
}