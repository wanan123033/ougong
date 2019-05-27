package com.ougong.shop.ActiivtyV2.B_clien.decoration_factory.mydesinger

import com.ougong.shop.ActiivtyV2.bean.DesingerBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface MyDesingerContract {


    interface View : ApiContract.View {

        fun getDesingerListSucess(data: Array<DesingerBean>)

    }

    interface Presenter : ApiContract.Presenter<View> {
        fun getDesingerList(mShowRole: Int)
    }
}