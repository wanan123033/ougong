package com.ougong.shop.activity.orderhistory

import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.OrderHistoryDetail
import com.ougong.shop.Bean.PayBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface OrderDetailContract {
    interface View:ApiContract.View{
        fun initOrderDetail(data: OrderHistoryDetail)
        fun reshData(it: Netbean<String>)
        fun buyItPay(it: Netbean<PayBean>)

    }
    interface Presenter:ApiContract.Presenter<OrderDetailContract.View>{
        fun getOrderDetail(orderId: Int)
        fun gotoPay(orderId: Int, i: Int)
        fun qrsh(orderId: Int)
        fun cannalOrder(orderId: Int)
        fun deleteOrder(orderId: Int)

    }
}