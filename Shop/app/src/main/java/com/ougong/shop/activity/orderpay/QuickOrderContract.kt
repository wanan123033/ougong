package com.ougong.shop.activity.orderpay

import com.ougong.shop.Bean.OrderBean
import com.ougong.shop.Bean.PayBean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.activity.Maininfo.mangeAdress.AddressBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface QuickOrderContract {
    interface View :ApiContract.View{
        fun getAdressSucess(it: fuckNetbean<AddressBean>)
        fun getOrderSucess(it: OrderBean)
        fun payPreSucess(data: PayBean)
    }

    interface Presenter :ApiContract.Presenter<QuickOrderContract.View>{
        fun getAdressList()
        fun sendOrder(id: String, quick: Boolean)
        fun buyIt(orderType:Int,addressId: Int, cardInfo: String?,comboData:String?, deliveryType: Int, payType: Int, remark: String?, quick: Boolean?,logisticsCompany:String?,ownLogisticsName:String?,ownLogisticsPhone:String?)
    }

}
