package com.ougong.shop.activity.Order

import android.text.TextUtils
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class OrderPresenter : ApiPresenter<OrderContract.View>(), OrderContract.Presenter {
    override fun BuyIt(orderType:Int,addressId: Int, id: String, deliveryType: Int, payType: Int, remark: String, quick: Boolean) {

        fetch(ServiceFactory.instance.productApiservice!!.BuyIt(orderType,addressId,id ,null,clientType = 2 , deliveryType = deliveryType,payType = payType,
            remark = remark,quick = false,
            ownLogisticsName = null,
            ownLogisticsPhone = null,
            logisticsCompany = null
        )) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.payPreSucess(it.data)
  //              ToastUtils.toast(App.app, "获取成功")
            }
            view?.hideLoading()
        }
    }

    override fun sendOrder(id: String, quick: Boolean) {
        fetch(ServiceFactory.instance.productApiservice!!.SendOrder(id, quick)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.getOrderSucess(it.data)
    //            ToastUtils.toast(App.app, "获取成功")
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