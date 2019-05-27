package com.ougong.shop.activity.checkin

import android.text.TextUtils
import android.util.Log
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class MyFanganPresenter : ApiPresenter<MyFanganActivityContract.View>(), MyFanganActivityContract.Presenter {
    override fun getMyFangan(limit:Int,page:Int) {
        fetch(ServiceFactory.instance.productApiservice!!.getMyFangan(limit,page)){
            Log.d("TAG",it.toString())
            if (it.status != 200){
                if(!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            }else{
                view?.initFangAnData(it)
            }
            view?.hideLoading()
        }
    }

}