package com.ougong.shop.activity.Maininfo

import io.armcha.ribble.presentation.base_mvp.api.ApiContract
import java.net.PasswordAuthentication

interface InfoContract {

    interface View : ApiContract.View{
//
//        fun startAuth()

        fun refeshOrderSucess()
    }

    interface Presenter : ApiContract.Presenter<View>{

        fun checkLogin()

        fun refeshOrder()
    }
}