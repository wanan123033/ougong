package com.ougong.shop.activity.Maininfo.mangeAdress.addAddress

import android.app.DownloadManager
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.TextureView
import android.view.View
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.ConstString
import com.ougong.shop.R
import com.ougong.shop.activity.Maininfo.mangeAdress.AddressBean
import com.ougong.shop.activity.Maininfo.mangeAdress.ChooseAddress.ChoosePlace
import com.ougong.shop.activity.Maininfo.mangeAdress.addAddressdatabaseBean
import com.ougong.shop.activity.Maininfo.mangeAdress.builder
import com.ougong.shop.base_mvp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.include_setting_back_title.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.http.FormUrlEncoded

class AddAddress : BaseActivity<AddAddressContract.view,AddAddressContract.presenter>(),AddAddressContract.view{
    override fun addAddressSucess() {
        ToastUtils.toast(this, "添加成功")
        finish()
    }

    override fun editAdressSucess() {

         }

    var bean  = builder().builde()
    override fun setContentViewSource() = R.layout.activity_add_address

    override fun initPresenter() = addAddressPresenter()

    var isSchooseMode = false
    override fun initView() {

        title_name.setText("增加收货地址")
        title_back.visibility = View.VISIBLE

        title_back.setOnClickListener { finish() }
        isSchooseMode = intent.getBooleanExtra(ConstString.CHOOSE_ADD_ADDRESS,false)

        StarusBarUtils.setStatusBarColor(this, R.color.f5f5f5)
        StarusBarUtils.setAndroidNativeLightStatusBar(this, true)

        choose_area.setOnClickListener {

            //start<ChoosePlace>()
            var intent = Intent(this,ChoosePlace::class.java)

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

            presenter.addAddress(bean)



            val thread:Thread = object : Thread(){
                override fun run() {
                    super.run()
                    val params:RequestBody = FormBody.Builder()
                        .add("address","下早新村10号")
                        .add("name","龚伟明")
                        .add("mobile","15814693089")
                        .add("isDefault","true")
                        .add("provinceId","13")
                        .add("cityId","34")
                        .add("districtId","3344")
                        .build()
                    val request = Request.Builder()
                        .url("https://ogmall.com/orderService/front/address/addAddress")
                        .post(params)
                        .header("Cookie","OG-FRONT-TOKEN=eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJnbGxsIiwidXNlcklkIjo3NDMyLCJwaG9uZSI6IjE1ODAwMDAwMDAxIiwidHlwZSI6MSwiZXhwIjoxNTU4Nzc2OTgzfQ.OiV4RnH2VeIllux69qyKQMQmYMLgTB4Fuz6127QF3tC4oe0duBWt4fjx1qAJm_XfUH3qxSTUiRqnnp6vsrx3WMfcvWFoQc0idm1FaIKeS3HUChDNUi7avnznyJzhsPCvzzb8NeixQL936ilgh-I2UGmuJ7FWwMIHiEfohqIL1ME")
                        .build()

                }
            }
            thread.start()

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
