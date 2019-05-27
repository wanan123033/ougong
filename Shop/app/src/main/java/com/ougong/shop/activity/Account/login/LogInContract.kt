package com.ougong.shop.activity.Account.login

import com.ougong.shop.Bean.User
import io.armcha.ribble.presentation.base_mvp.api.ApiContract
import java.net.PasswordAuthentication

interface LogInContract {

    interface View :ApiContract.View{

        fun startAuth()

        fun back()

        fun loginSucess(data: User)

        fun refeshUserInfosucess(data: User)
    }

    interface Presenter : ApiContract.Presenter<View>{

        fun makeLogin(passwordAuthentication: PasswordAuthentication)

        fun checkLogin()

        fun RefeshInfo()
    }
}