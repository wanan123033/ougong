package com.ougong.shop.activity.MainShopCarFragment.ChooseAddress

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.ConstString
import com.ougong.shop.R
import com.ougong.shop.activity.Maininfo.mangeAdress.AddressAdapter
import com.ougong.shop.activity.Maininfo.mangeAdress.AddressBean
import com.ougong.shop.activity.Maininfo.mangeAdress.ManageAdressContract
import com.ougong.shop.activity.Maininfo.mangeAdress.ManageAdressPresenter
import com.ougong.shop.activity.Maininfo.mangeAdress.addAddress.AddAddress
import com.ougong.shop.activity.Maininfo.mangeAdress.editAdress.EditAdressActivity
import com.ougong.shop.base_mvp.base.BaseActivity
import com.ougong.shop.utils.BottomdialogConfime
import io.armcha.ribble.presentation.utils.extensions.start
import kotlinx.android.synthetic.main.activity_mange_adress.*
import kotlinx.android.synthetic.main.include_setting_back_title.*

/**
 * 这是购物车没地址，选择地址的activity，其实所有逻辑和 {@link MangeAdress } 一样
 * 但是实在是懒得处理这件事，直接分开搞，多几个类。
 */
class ChooseAddress : BaseActivity<ManageAdressContract.View, ManageAdressContract.Presenter>(),
    ManageAdressContract.View {
    override fun getAdressSucess(it: fuckNetbean<AddressBean>) {
        adapter.setData(it.data)
    }

    override fun setContentViewSource() = R.layout.activity_mange_adress

    override fun initPresenter() = ManageAdressPresenter()

    var mCheckID = -1
    override fun initData() {
        mCheckID = intent.getIntExtra(ConstString.CHOOSE_ADDRESS_HANDLE,-1)
        adapter.CheckId = mCheckID
        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        presenter.getAdressList()
    }

    var adapter = ChooseAddressAdapter()

    override fun initView() {

        StarusBarUtils.setStatusBarColor(this, R.color.f5f5f5)
        StarusBarUtils.setAndroidNativeLightStatusBar(this, true)

        title_name.setText("选择收货地址")
        recycleView.adapter = adapter

        adapter.mItemclick = object : ChooseAddressAdapter.itemclick {
            override fun Choose(position: Int) {
                val intent = Intent()
                intent.putExtra(ConstString.CHOOSE_ADDRESS_RESULT_ID,adapter.getData(position))
                setResult(ConstString.CHOOSE_ADDRESS_RESULT,intent)
                finish()
            }

            override fun edit(position: Int) {

                //start<EditAdressActivity>()
                //start<EditAdressActivity>()
                val intent = Intent(this@ChooseAddress, EditAdressActivity::class.java)
                intent.putExtra("address", adapter.getData(position))

                startActivity(intent)
            }

        }
        recycleView.layoutManager = LinearLayoutManager(this)

        addAddress.setOnClickListener {
            start<AddAddress>()
        }
    }
}
