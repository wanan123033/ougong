package com.ougong.shop.activity.orderhistory

import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.ConstString
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class OrderHistoryPresenter : ApiPresenter<OrderHistoryContract.View>(),OrderHistoryContract.Presenter {
    override fun qrsh(orderId: Int) {
        fetch(ServiceFactory.instance.productApiservice!!.qrsh(orderId)){
            if (it.status != 200){
                ToastUtils.toast(App.app,it.message)
            }else{
                ToastUtils.toast(App.app,"确认收货成功")
                view?.reshData(it)
            }
        }
    }

    override fun cannalOrder(orderId: Int) {
        fetch(ServiceFactory.instance.productApiservice!!.cannalOrder(orderId)){
            if (it.status != 200){
                ToastUtils.toast(App.app,it.message)
            }else{
                ToastUtils.toast(App.app,"订单取消成功")
                view?.reshData(it)
            }
        }
    }


    override fun gotoPay(orderIds: Int,payType:Int) {
        fetch(ServiceFactory.instance.productApiservice!!.gotoPay(2,orderIds,payType)){
            if (it.status != 200){
                ToastUtils.toast(App.app,it.message)
            }else{
                view?.buyItPay(it)
            }
        }
    }

    override fun deleteOrder(id: Int) {
        fetch(ServiceFactory.instance.productApiservice!!.deleteOrder(id)){
            if (it.status != 200){
                ToastUtils.toast(App.app,it.message)
            }else{
                ToastUtils.toast(App.app,"订单删除成功")
                view?.reshData(it)
            }
        }
    }

    override fun getHistoryList(page: Int?, status: Int?) {
        fetch(ServiceFactory.instance.productApiservice!!.getHistoryList(page, ConstString.PAGESIZE,status)){
            if (it.status != 200){
                ToastUtils.toast(App.app,it.message)
            }else{
                view?.initHistoryList(it)
            }
        }
    }
}