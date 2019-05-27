package com.ougong.shop.ActiivtyV2.B_clien.cheng_yun_shang.myMoney

import com.ougong.shop.ActiivtyV2.bean.DesingerBean
import com.ougong.shop.ActiivtyV2.bean.MOneyBeanDetail
import com.ougong.shop.ActiivtyV2.bean.MyMOneyBean
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

interface myMoneyContract {

    interface View : ApiContract.View {

        fun getMonyeySucess(data: MyMOneyBean)
        fun TakeOutSucess()
        fun MoneyDeatailSucess(data: Array<MOneyBeanDetail>)
    }

    interface Presenter : ApiContract.Presenter<View> {
        fun getMonyey()
        fun TakeOut(no : Int)
        fun MoneyDeatail(endDate : String?, startDate : String?, type : Int?)

    }
}