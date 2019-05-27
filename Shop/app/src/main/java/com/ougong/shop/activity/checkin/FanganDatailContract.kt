package com.ougong.shop.activity.checkin

import com.ougong.shop.Bean.Netbean
import com.ougong.shop.httpmodule.CheckLinFanganDatailBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface FanganDatailContract {
    interface View : ApiContract.View{
        fun initFanganDatail(bean:Netbean<CheckLinFanganDatailBean>)
        fun deleteFanganSucess(bean:Netbean<String>)
    }

    interface Presenter : ApiContract.Presenter<FanganDatailContract.View> {
        fun getFanganDatail(id:Int)
        fun deleteFangan(id:Int)
    }

}
