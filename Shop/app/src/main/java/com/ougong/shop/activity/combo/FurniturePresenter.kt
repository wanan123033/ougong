package com.ougong.shop.activity.combo

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class FurniturePresenter : ApiPresenter<FurnitureActivityContract.View>(), FurnitureActivityContract.Presenter{
    override fun getOneTitle() {
        fetch(ServiceFactory.instance.productApiservice!!.comboOneTitle()){
            if (it.status != 200){
                if(!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            }else{
                view?.initTitle(it)
                //              ToastUtils.toast(App.app,"获取成功")
            }
            view?.hideLoading()

        }
    }

}