package com.ougong.shop.activity.Product

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory


class ProductPresenter : ApiPresenter<ProductContract.View>(), ProductContract.Presenter {
    override fun getProductDetails(id: Int) {
        fetch(ServiceFactory.instance.productApiservice!!.getProductDetail(id = id)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.getProductSucess(it)
  //              ToastUtils.toast(App.app, "获取成功")
            }
            view?.hideLoading()

        }


    }


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
                view?.getProperSucess(it.data)
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