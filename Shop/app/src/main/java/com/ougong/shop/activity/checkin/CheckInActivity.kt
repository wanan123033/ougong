package com.ougong.shop.activity.checkin

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.R
import com.ougong.shop.base_mvp.base.BaseActivity
import com.ougong.shop.httpmodule.HxBean
import com.ougong.shop.httpmodule.OrderHistoryJavaBean
import io.armcha.ribble.presentation.utils.extensions.start
import kotlinx.android.synthetic.main.activity_check_in.*

class CheckInActivity : BaseActivity<CheckInContract.View,CheckInContract.Presenter>(),CheckInContract.View,
    View.OnClickListener {
    override fun initHistoryList(bean: Netbean<OrderHistoryJavaBean>) {

    }

    override fun initHxData(bean: fuckNetbean<HxBean>) {
        hxDialog!!.setHxData(bean.data)
    }

    override fun setContentViewSource() = R.layout.activity_check_in

    override fun initPresenter() = CheckInPresenter()



    var hxDialog:HxDialog? = null
    var hxData:HxBean? = null

    override fun initView() {
        if (!CheckLinUtils.getInstance().isNoFirst()){
            val intent = Intent(this,CheckInFanganActivity::class.java)
            intent.putExtra(CheckInFanganActivity.FANGAN_ID,CheckLinUtils.getInstance().checkHx.id)
            start(intent)
            finish()
        }

        hxDialog = HxDialog()
        findViewById<View>(R.id.title_back).visibility = View.VISIBLE
        findViewById<View>(R.id.title_back).setOnClickListener{
            finish()
        }

        findViewById<View>(R.id.tv_fangan).setOnClickListener(this)
        findViewById<View>(R.id.rl_hx).setOnClickListener{
            hxDialog!!.show(supportFragmentManager,"hxDialog")
        }

        presenter.getHxData()
    }

    override fun onClick(v: View?) {
        val name = et_name.text.toString().trim()
        val area = et_area.text.toString().trim()
        if (TextUtils.isEmpty(name)){
            ToastUtils.toast(this,"请输入方案名称")
            return
        }
        if (TextUtils.isEmpty(area)){
            ToastUtils.toast(this,"请输入面积")
            return
        }
        if (hxData == null){
            ToastUtils.toast(this,"请选择户型")
            return
        }
        val util:CheckLinUtils = CheckLinUtils.getInstance()
        util.setCheckArea(Integer.parseInt(area))
        util.checkLinName = name
        util.setCheckHxData(hxData!!.name)
        util.setCheckHx(hxData)

        val intent = Intent(this,CheckInFanganActivity::class.java)
        intent.putExtra(CheckInFanganActivity.FANGAN_ID,hxData!!.id)
        start(intent)
//        presenter.getHistoryList(1, null)

    }

    fun setHx(item:HxBean) {
        this.hxData = item
        tv_hx.text = item.name
    }
}