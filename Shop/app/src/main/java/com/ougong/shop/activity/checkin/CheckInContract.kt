package com.ougong.shop.activity.checkin

import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.httpmodule.HxBean
import com.ougong.shop.httpmodule.OrderHistoryJavaBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface CheckInContract {
    interface View:ApiContract.View{
        fun initHxData(bean: fuckNetbean<HxBean>)
        fun initHistoryList(bean:Netbean<OrderHistoryJavaBean>)
    }
    interface Presenter : ApiContract.Presenter<CheckInContract.View>{
        fun getHxData()
        fun getHistoryList(page: Int, status:Int?)
    }
}
