package com.ougong.shop.activity.checkin

import com.ougong.shop.Bean.Netbean
import com.ougong.shop.httpmodule.MyCheckLinFanganBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface MyFanganActivityContract {
    interface View : ApiContract.View{
        fun initFangAnData(bean: Netbean<MyCheckLinFanganBean>)
    }
    interface Presenter : ApiContract.Presenter<MyFanganActivityContract.View>{
        fun getMyFangan(limit:Int,page:Int)
    }
}
