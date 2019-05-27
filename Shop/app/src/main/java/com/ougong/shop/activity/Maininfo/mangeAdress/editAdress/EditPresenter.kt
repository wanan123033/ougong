package com.ougong.shop.activity.Maininfo.mangeAdress.editAdress

import android.text.TextUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.App
import com.ougong.shop.activity.Maininfo.mangeAdress.AddressBean
import com.ougong.shop.activity.Maininfo.mangeAdress.addAddress.AddAddressContract
import com.ougong.shop.base_mvp.api.ApiPresenter
import com.ougong.shop.httpmodule.ServiceFactory

class EditPresenter  : ApiPresenter<EditAdressContract.view>(), EditAdressContract.presenter {

    override fun addAddress(address: AddressBean) {

        fetch(
            ServiceFactory.instance.userInfoApiService!!.addAddress(address = address.address,cityId = address.cityId,districtId = address.districtId,
                isDefault = address.isDefault,mobile = address.mobile,name = address.name,provinceId = address.provinceId)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.addAddressSucess()
            }
            view?.hideLoading()

        }
    }


    override fun editAddress(address: AddressBean) {

        fetch(
            ServiceFactory.instance.userInfoApiService!!.updateAddress(address = address.address,cityId = address.cityId,districtId = address.districtId,
                id = address.id,
                isDefault = address.isDefault,mobile = address.mobile,name = address.name,provinceId = address.provinceId)) {
            if (it.status != 200) {
                if (!TextUtils.isEmpty(it.message)) {
                    ToastUtils.toast(App.app, it.message)
                }
            } else {
                view?.editAdressSucess()
            }
            view?.hideLoading()

        }
    }


    override fun onRequestError(errorMessage: String?) {
        super.onRequestError(errorMessage)
        ToastUtils.toast(App.app, errorMessage)
    }
}