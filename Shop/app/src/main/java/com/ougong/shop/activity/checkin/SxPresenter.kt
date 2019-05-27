package com.ougong.shop.activity.checkin

import android.util.Log
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class SxPresenter : ApiPresenter<SxDialogContract.View>(){
    fun getStyleData(brandCategoryId:Int?) {
        fetch(ServiceFactory.instance.productApiservice!!.getStyleList(brandCategoryId)){
            Log.d("TAG",it.toString())
            if (it.status != 200){
                ToastUtils.toast(App.app, it.message)
            }else{
                view?.initStyleList(it)
            }
            view?.hideLoading()
        }
    }

    fun getBrandData(categoryId: Int?, styleId: Int?) {
        fetch(ServiceFactory.instance.productApiservice!!.getBrandCategorys(categoryId,limit = 200,searchWord = null,styleId = styleId)){
            Log.d("TAG",it.toString())
            if (it.status != 200){
                ToastUtils.toast(App.app, it.message)
            }else{
                view?.initBrandList(it)
            }
            view?.hideLoading()
        }
    }
}