package com.ougong.shop.activity.orderhistory

import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.PayBean
import com.ougong.shop.httpmodule.OrderHistoryJavaBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface OrderHistoryContract {
    interface View:ApiContract.View{
        fun initHistoryList(bean:Netbean<OrderHistoryJavaBean>)
        fun reshData(it: Netbean<String>)
        fun buyItPay(it: Netbean<PayBean>)
    }
    interface Presenter:ApiContract.Presenter<OrderHistoryContract.View>{
        fun getHistoryList(page: Int?, status: Int?)
        fun qrsh(orderId: Int)
        fun cannalOrder(orderId: Int)
        fun gotoPay(orderIds: Int,payType:Int)
        fun deleteOrder(id: Int)
    }
}