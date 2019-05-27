package com.ougong.shop.activity.Maininfo.mangeAdress

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.R
import com.ougong.shop.activity.Maininfo.mangeAdress.addAddress.AddAddress
import com.ougong.shop.activity.Maininfo.mangeAdress.editAdress.EditAdressActivity
import com.ougong.shop.base_mvp.base.BaseActivity
import com.ougong.shop.utils.BottomDialog
import com.ougong.shop.utils.BottomdialogConfime
import io.armcha.ribble.presentation.utils.extensions.start
import kotlinx.android.synthetic.main.activity_mange_adress.*
import kotlinx.android.synthetic.main.include_setting_back_title.*

/**
 *
 */
class MangeAdress : BaseActivity<ManageAdressContract.View,ManageAdressContract.Presenter>() ,ManageAdressContract.View{
    override fun getAdressSucess(it: fuckNetbean<AddressBean>) {
        adapter.setData(it.data)
    }

    override fun setContentViewSource() = R.layout.activity_mange_adress

    override fun initPresenter() = ManageAdressPresenter()

    override fun initData() {
        title_back.visibility =View.VISIBLE
        title_back.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        presenter.getAdressList()
    }

    var adapter = AddressAdapter()

    override fun initView() {

        StarusBarUtils.setStatusBarColor(this, R.color.f5f5f5)
        StarusBarUtils.setAndroidNativeLightStatusBar(this, true)

        title_name.setText("地址管理")
        recycleView.adapter = adapter

        adapter.mItemclick = object : AddressAdapter.itemclick{
            override fun delect(position: Int) {

                val d = BottomdialogConfime(this@MangeAdress,"您确定要删除该收货人地址吗？")
                d.clickListenerInterface = object : BottomdialogConfime.ClickListenerInterface{
                    override fun doConfirm() {
                        presenter.deleteAdress(adapter.getData(position).id)
                    }

                    override fun doCancel() {

                    }

                }
                d.show()
            }

            override fun edit(position: Int) {

                //start<EditAdressActivity>()
                val intent = Intent(this@MangeAdress,EditAdressActivity::class.java)
                intent.putExtra("address",adapter.getData(position))

                startActivity(intent)
                 }

        }
        recycleView.layoutManager = LinearLayoutManager(this)

        addAddress.setOnClickListener {
               start<AddAddress>()
        }

    }
}
