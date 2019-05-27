package com.ougong.shop.activity.combo

import com.ougong.shop.Bean.Netbean
import com.ougong.shop.MainActivityContract
import com.ougong.shop.httpmodule.ComboTitleBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

class FurnitureActivityContract {
    interface View : ApiContract.View {
        fun initTitle(bean: Netbean<ComboTitleBean>)
    }
    interface Presenter : ApiContract.Presenter<FurnitureActivityContract.View> {
        fun getOneTitle()
    }
}
