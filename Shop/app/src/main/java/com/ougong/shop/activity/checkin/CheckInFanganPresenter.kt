package com.ougong.shop.activity.checkin

import android.text.TextUtils
import android.util.Log
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory
import okhttp3.RequestBody

class CheckInFanganPresenter : ApiPresenter<CheckInFanganContract.View>(),CheckInFanganContract.Presenter {
    override fun shoucang(body: RequestBody) {
        fetch(ServiceFactory.instance.productApiservice!!.saveCheckLinFangan(body)){
            Log.d("TAG",it.toString())
            if (it.status != 200){
                ToastUtils.toast(App.app, it.message)
            }else{
                view?.requestSucessData(it)
            }
            view?.hideLoading()
        }
    }

    override fun saveCheckLinFangan(body: RequestBody) {
        fetch(ServiceFactory.instance.productApiservice!!.saveCheckLinFangan(body)){
            Log.d("TAG",it.toString())
            if (it.status != 200){
                ToastUtils.toast(App.app, it.message)
            }else{
                view?.requestSucessData(it)
            }
            view?.hideLoading()
        }
    }

    override fun getHouseSpace(hxId: Int?) {
        fetch(ServiceFactory.instance.productApiservice!!.getHouseSpace(hxId)){
            Log.d("TAG",it.toString())
            if (it.status != 200){
                if(!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            }else{
                view?.initHouseSpace(it)
            }
            view?.hideLoading()
        }
    }
    override fun getAllList() {
        fetch(ServiceFactory.instance.productApiservice!!.getCateryBean()) {

            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.initCategory(it)
            }
            view?.hideLoading()

        }
    }
}