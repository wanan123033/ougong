package com.ougong.shop.activity.Maininfo.mangeAdress.editAdress

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.R
import com.ougong.shop.activity.Maininfo.mangeAdress.AddressBean
import com.ougong.shop.activity.Maininfo.mangeAdress.ChooseAddress.ChoosePlace
import com.ougong.shop.activity.Maininfo.mangeAdress.addAddress.AddAddressContract
import com.ougong.shop.activity.Maininfo.mangeAdress.addAddress.addAddressPresenter
import com.ougong.shop.activity.Maininfo.mangeAdress.builder
import com.ougong.shop.base_mvp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_address.*
import kotlinx.android.synthetic.main.include_setting_back_title.*

class EditAdressActivity: BaseActivity<AddAddressContract.view, AddAddressContract.presenter>(), AddAddressContract.view{
    override fun addAddressSucess() {
    }

    override fun editAdressSucess() {

        ToastUtils.toast(this, "更新成功")
        finish()

    }

    var bean  = builder().builde()
    override fun setContentViewSource() = R.layout.activity_edit_address

    override fun initPresenter() = addAddressPresenter()

    override fun initView() {

        title_name.setText("编辑收货地址")
        title_back.visibility = View.VISIBLE

        title_back.setOnClickListener { finish() }
        StarusBarUtils.setStatusBarColor(this, R.color.f5f5f5)
        StarusBarUtils.setAndroidNativeLightStatusBar(this, true)
        bean = intent.getSerializableExtra("address") as AddressBean

        choose_area.setText(bean.addressInfo.removeSuffix(bean.address))
        name.setText(bean.name)
        phone_num.setText(bean.mobile)
        full_adress.setText(bean.address)
        isDefault.isChecked = bean.isDefault
        choose_area.setOnClickListener {

            //start<ChoosePlace>()
            var intent = Intent(this, EditChooseActivity::class.java)

            intent.putExtra("address",bean)
            startActivityForResult(intent,222)
        }

        addAddress.setOnClickListener {

            if (TextUtils.isEmpty(name.text.toString())){
                ToastUtils.toast(this,"请输入姓名")
                return@setOnClickListener
            }


            if (TextUtils.isEmpty(phone_num.text.toString())){
                ToastUtils.toast(this,"请填入电话")
                return@setOnClickListener
            }


            if (TextUtils.isEmpty(full_adress.text.toString())){
                ToastUtils.toast(this,"请输入详细地址")

                return@setOnClickListener
            }

            if (bean.provinceId == -1){
                ToastUtils.toast(this,"请想选择区域")

                return@setOnClickListener
            }

            bean.address = full_adress.text.toString()
            bean.name = name.text.toString()
            bean.mobile = phone_num.text.toString()
            bean.isDefault = isDefault.isChecked

            presenter.editAddress(bean)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){
            222->{
                if(resultCode == 223){

                    if (data != null) {
                        bean.provinceId = data!!.getIntExtra("data1",-1)
                        bean.cityId = data!!.getIntExtra("data2",-1)
                        bean.districtId = data!!.getIntExtra("data3",-1)

                        choose_area.setText(data!!.getStringExtra("data4"))
                    }
                }
            }
        }
    }
}
