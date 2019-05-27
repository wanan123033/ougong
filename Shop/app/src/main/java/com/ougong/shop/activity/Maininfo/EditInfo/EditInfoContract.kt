package com.ougong.shop.activity.Maininfo.EditInfo

import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.User
import io.armcha.ribble.presentation.base_mvp.api.ApiContract

class EditInfoContract  {

    interface View : ApiContract.View{

        fun updataSucess()

        fun uploadPicSicess(it: String)
    }

    interface Presenter : ApiContract.Presenter<View>{

        fun updataInfo(user: User)

        fun uploadImage(path: String)
    }
}