package com.ougong.shop

import com.ougong.shop.Bean.Netbean
import com.ougong.shop.httpmodule.CheckVersion
import com.ougong.shop.httpmodule.OrderHistoryJavaBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

class MainActivityContract {

    interface View : ApiContract.View {

        fun UpGradeSucess(data: CheckVersion)
    }

    interface Presenter : ApiContract.Presenter<View> {

        fun CheckVersion()
    }
}