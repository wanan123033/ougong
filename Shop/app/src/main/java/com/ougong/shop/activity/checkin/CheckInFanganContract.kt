package com.ougong.shop.activity.checkin

import com.ougong.shop.Bean.CateryBean
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.httpmodule.HxSpaceBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract
import okhttp3.RequestBody

interface CheckInFanganContract {
    interface View : ApiContract.View{
        fun initHouseSpace(bean: fuckNetbean<HxSpaceBean>)
        fun requestSucessData(bean: Netbean<String>)
        fun initCategory(bean: fuckNetbean<CateryBean>)
    }
    interface Presenter : ApiContract.Presenter<CheckInFanganContract.View>{
        fun getHouseSpace(hxId:Int?)
        fun saveCheckLinFangan(body:RequestBody)
        fun getAllList()
        fun shoucang(body:RequestBody)
    }
}
