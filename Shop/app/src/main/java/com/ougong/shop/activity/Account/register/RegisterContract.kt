package com.ougong.shop.activity.Account.register


import io.armcha.ribble.presentation.base_mvp.api.ApiContract
import java.net.PasswordAuthentication

interface RegisterContract{


    interface View : ApiContract.View{

        fun RegisterSucess()
        fun CheckPhoneSucess()

        fun checkNOSucess()

    }

    interface Presenter : ApiContract.Presenter<View>{

        fun register(passwordAuthentication: PasswordAuthentication,smsCode : String)

        fun sendCheckNo(phoneNumber: String)

        fun CheckPhoneNo(smsCode : String)
    }
}