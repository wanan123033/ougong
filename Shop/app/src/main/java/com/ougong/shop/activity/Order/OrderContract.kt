package com.ougong.shop.activity.Order

import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.OrderBean
import com.ougong.shop.Bean.PayBean
import com.ougong.shop.Bean.ProductDetails
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

class OrderContract {
    interface View : ApiContract.View{

        fun getOrderSucess(it: OrderBean)


        fun payPreSucess(it: PayBean)
    }

    interface Presenter : ApiContract.Presenter<View>{
        fun sendOrder(id: String, quick: Boolean)

        fun BuyIt(orderType:Int,addressId : Int, id: String, deliveryType : Int,payType: Int,remark : String ,quick : Boolean = true)
    }
}