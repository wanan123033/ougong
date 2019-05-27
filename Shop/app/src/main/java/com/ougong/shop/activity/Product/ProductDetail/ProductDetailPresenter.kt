package com.ougong.shop.activity.Product.ProductDetail

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory
import java.net.PasswordAuthentication

class ProductDetailPresenter : ApiPresenter<ProductDetailContract.View>(), ProductDetailContract.Presenter {
    override fun getDetails(id : Int) {
        fetch(ServiceFactory.instance.productApiservice!!.getProductDetail1(id)){
            if (it.status != 200){
                if(!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            }else{
                view?.getDetailSucess(it.data)
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

    override fun onRequestStart() {
        view?.showLoading()
        view?.apply{
        }
    }


}