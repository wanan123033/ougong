package com.ougong.shop.activity.combo

import com.ougong.shop.Bean.Netbean
import com.ougong.shop.httpmodule.ComboDatailBean
import com.ougong.shop.httpmodule.RecommendBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract
import okhttp3.RequestBody

class ComboDatailActivityContract {
    interface View :ApiContract.View{
        fun initComboDatail(bean: Netbean<ComboDatailBean>?)
        fun reshRecommendData(bean:Netbean<RecommendBean>)
    }

    interface Presenter:ApiContract.Presenter<ComboDatailActivityContract.View>  {
        fun getComboDatail(id:Int)
        fun getRecommendData(body:RequestBody)
    }

}
