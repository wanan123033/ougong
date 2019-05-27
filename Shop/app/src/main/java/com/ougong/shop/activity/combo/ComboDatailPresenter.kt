package com.ougong.shop.activity.combo

import android.text.TextUtils
import android.util.Log
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory
import okhttp3.RequestBody

class ComboDatailPresenter : ApiPresenter<ComboDatailActivityContract.View>(), ComboDatailActivityContract.Presenter{
    override fun getRecommendData(body: RequestBody) {
        Log.d("TAG14","---------")
        fetch(ServiceFactory.instance.productApiservice!!.recommendData(body)){
            Log.d("TAG",it.toString())
            if (it.status != 200){
                if(!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            }else{
                view?.reshRecommendData(it)
            }
            view?.hideLoading()
        }
    }

    override fun getComboDatail(id: Int) {
        fetch(ServiceFactory.instance.productApiservice!!.comboDatail(id)){
            if (it.status != 200){
                if(!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            }else{
                view?.initComboDatail(it)
            }
            view?.hideLoading()
        }
    }


}