package com.ougong.shop.ActiivtyV2.B_clien.vip_designer.EditInfo

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