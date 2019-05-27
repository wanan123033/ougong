package com.ougong.shop.activity.checkin

import android.text.TextUtils
import android.util.Log
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class CheckInPresenter : ApiPresenter<CheckInContract.View>(),CheckInContract.Presenter {
    override fun getHistoryList(page: Int, status: Int?) {
        fetch(ServiceFactory.instance.productApiservice!!.getHistoryList(page,18,status)){
            if (it.status != 200){
                ToastUtils.toast(App.app,it.message)
            }else{
                view?.initHistoryList(it)
            }
        }
    }

    override fun getHxData() {
        fetch(ServiceFactory.instance.productApiservice!!.getHxData()){
            Log.d("TAG",it.toString())
            if (it.status != 200){
                if(!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            }else{
                view?.initHxData(it)
            }
            view?.hideLoading()
        }
    }
}