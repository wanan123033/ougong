package com.ougong.shop.activity.checkin

import android.util.Log
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class FanganDatailPresenter : ApiPresenter<FanganDatailContract.View>(), FanganDatailContract.Presenter {
    override fun getFanganDatail(id: Int) {
        fetch(ServiceFactory.instance.productApiservice!!.getFanganDatail(id)){
            Log.d("TAG",it.toString())
            if (it.status != 200){
                ToastUtils.toast(App.app, it.message)
            }else{
                view?.initFanganDatail(it)
            }
            view?.hideLoading()
        }
    }

    override fun deleteFangan(id: Int) {
        fetch(ServiceFactory.instance.productApiservice!!.deleteFangan(id)){
            Log.d("TAG",it.toString())
            if (it.status != 200){
                ToastUtils.toast(App.app, it.message)
            }else{
                view?.deleteFanganSucess(it)
            }
            view?.hideLoading()
        }
    }

}
