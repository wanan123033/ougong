package com.ougong.shop.activity.Account

import io.armcha.ribble.presentation.base_mvp.api.ApiContract
import java.net.PasswordAuthentication

class AccountPresentContract {


    interface View : ApiContract.View{

        fun Sucess()

        fun sendCheckNoSucess(){}

        fun ChangePhoneSucess(){}

        fun ChangePasswordSucess(){}

        fun ForgetPasswordSucess(){}

        fun CheckPhoneSucess(havePhoneRecord : Boolean){}
    }

    interface Presenter : ApiContract.Presenter<View>{

        fun register(passwordAuthentication: PasswordAuthentication, smsCode : String)

        fun sendCheckNo(phoneNumber: String,type : Int)

        fun CheckPhoneNo(smsCode: String)

        fun changePhone(password: String,phone: String, mscode: String)

        fun changePassword(oldPassword : String,password : String)

        fun forgetPassword(phone: String,checknu: String,password: String)
    }

}