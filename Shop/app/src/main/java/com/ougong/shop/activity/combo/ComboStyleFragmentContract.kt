package com.ougong.shop.activity.combo

import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.httpmodule.ComboBean
import com.ougong.shop.httpmodule.ComboStyleBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

class ComboStyleFragmentContract {
    interface View : ApiContract.View {
        fun initStyleData(bean : fuckNetbean<ComboStyleBean>)
        fun initComboData(bean : Netbean<ComboBean>)
    }
    interface Presenter : ApiContract.Presenter<ComboStyleFragmentContract.View> {
        fun getStyleData(id:Int?)
        fun getComboData(id:Int?,page:Int,comboTypeId:Int?)
    }
}
