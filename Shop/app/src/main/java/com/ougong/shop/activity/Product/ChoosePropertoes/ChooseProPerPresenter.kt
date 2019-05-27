package com.ougong.shop.activity.Product.ChoosePropertoes

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class ChooseProPerPresenter : ApiPresenter<ChoosePropertoesContract.View>(), ChoosePropertoesContract.Presenter {
    override fun addShopCar(id: Int, count: Int) {

        fetch(ServiceFactory.instance.productApiservice!!.addShopCar(count,id)){
            if (it.status != 200){
                if(!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            }else{
                view?.addSucess()
            }
            view?.hideLoading()

        }

    }

    override fun getProper(id: Int) {


        fetch(ServiceFactory.instance.productApiservice!!.getProductSpec(id)){
            if (it.status != 200){
                if(!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            }else{
                view?.getSucess(it.data)
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
        view?.apply {
        }
    }
}

