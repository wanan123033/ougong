package com.ougong.shop.activity.checkin

import android.util.Log
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class EditFanganPresenter :ApiPresenter<EditFanganFenleiContract.View>(), EditFanganFenleiContract.Presenter {
    override fun getAllOneCategory() {
        fetch(ServiceFactory.instance.productApiservice!!.getCateryBean()){
            Log.d("TAG",it.toString())
            if (it.status != 200){
                ToastUtils.toast(App.app, it.message)
            }else{
                view?.initOneCategory(it)
            }
            view?.hideLoading()
        }
    }

}
