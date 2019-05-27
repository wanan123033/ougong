package com.ougong.shop.ActiivtyV2.B_clien.cheng_yun_shang.myMoney

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.TimeUtils
import com.jzxiang.pickerview.TimePickerDialog
import com.jzxiang.pickerview.data.Type
import com.jzxiang.pickerview.listener.OnDateSetListener
import com.ougong.shop.ActiivtyV2.bean.MOneyBeanDetail
import com.ougong.shop.ActiivtyV2.bean.MyMOneyBean
import com.ougong.shop.R
import com.ougong.shop.base_mvp.base.BaseActivity
import io.armcha.ribble.presentation.utils.extensions.takeColor
import kotlinx.android.synthetic.main.activity_my_money.*

class MyMoneyActivity : BaseActivity<myMoneyContract.View, myMoneyContract.Presenter>(), myMoneyContract.View, OnClickCallBack {

    var fragment : TakeOutDialogFragment? = null
    override fun OnSearchClick() {
        presenter.MoneyDeatail(mAdapter?.myMOneyBean?.endTime,mAdapter?.myMOneyBean?.startTime,mAdapter?.myMOneyBean?.SearchType)
    }

    fun makesureTakeOutMoney(no : Int){
        presenter.TakeOut(1000)
    }
    override fun OnStartTimeClick() {
        val handerYears = 10L * 365 * 1000 * 60 * 60 * 24L
        val threYears = 0
        val mDialogYearMonthDay = TimePickerDialog.Builder()
            .setType(Type.YEAR_MONTH_DAY)
            .setCurrentMillseconds(System.currentTimeMillis() - threYears)
            .setCallBack(object : OnDateSetListener{
                override fun onDateSet(timePickerView: TimePickerDialog?, millseconds: Long) {
                    TimeUtils.DateTimeyyyy_mm_dd(millseconds)
                    mAdapter?.myMOneyBean?.startTime = TimeUtils.DateTimeyyyy_mm_dd(millseconds)

                    mAdapter?.notifyDataSetChanged()
                }

            })
            .setThemeColor(takeColor(R.color.white))
            .setDayText("日")
            .setTitleStringId("选择开始日期")
            .setMinMillseconds(System.currentTimeMillis() - handerYears)
            .setMaxMillseconds(System.currentTimeMillis())
            .setMonthText("月")
            .setYearText("年")
            .setWheelItemTextSize(15)
            .setWheelItemTextNormalColor(takeColor(R.color.j666))
            .setWheelItemTextSelectorColor(takeColor(R.color.j000))
            .build()
        mDialogYearMonthDay.show(supportFragmentManager, "------")

    }

    override fun OnEndTimeClick() {
        val handerYears = 10L * 365 * 1000 * 60 * 60 * 24L
        val threYears = 0
        val mDialogYearMonthDay = TimePickerDialog.Builder()
            .setType(Type.YEAR_MONTH_DAY)
            .setCurrentMillseconds(System.currentTimeMillis() - threYears)
            .setCallBack(object : OnDateSetListener{
                override fun onDateSet(timePickerView: TimePickerDialog?, millseconds: Long) {
                    TimeUtils.DateTimeyyyy_mm_dd(millseconds)
                    mAdapter?.myMOneyBean?.endTime = TimeUtils.DateTimeyyyy_mm_dd(millseconds)

                    mAdapter?.notifyDataSetChanged()
                }

            })
            .setThemeColor(takeColor(R.color.white))
            .setDayText("日")
            .setTitleStringId("选择结束日期")
            .setMinMillseconds(System.currentTimeMillis() - handerYears)
            .setMaxMillseconds(System.currentTimeMillis())
            .setMonthText("月")
            .setYearText("年")
            .setWheelItemTextSize(15)
            .setWheelItemTextNormalColor(takeColor(R.color.j666))
            .setWheelItemTextSelectorColor(takeColor(R.color.j000))
            .build()
        mDialogYearMonthDay.show(supportFragmentManager, "------")

    }

    override fun OnTakeOutMoneyClick() {
        fragment = TakeOutDialogFragment()
        fragment?.show(supportFragmentManager,"login")

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mAdapter?.myMOneyBean?.SearchType= position

    }

    override fun getMonyeySucess(data: MyMOneyBean) {
        mAdapter?.myMOneyBean = data
        mAdapter?.notifyDataSetChanged()
    }

    override fun TakeOutSucess() {

    }

    override fun MoneyDeatailSucess(data: Array<MOneyBeanDetail>) {
        LogUtils.e("" + data.size, this)

        mAdapter?.mList = data

        LogUtils.e("" + data.size, this)
        mAdapter?.notifyDataSetChanged()
    }

    override fun setContentViewSource() = R.layout.activity_my_money

    override fun initPresenter() = myMoneyPresenter()


    var mAdapter: MyMoneyAdapter? = null
    override fun initView() {
        super.initView()
        StarusBarUtils.setStatusBarColor(this, R.color.black)
        StarusBarUtils.setAndroidNativeLightStatusBar(this, false)
        title_back.setOnClickListener { finish() }
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = MyMoneyAdapter(this)
        mAdapter!!.onClickCallBack = this
        mRecyclerView.adapter = mAdapter

        presenter.getMonyey()
        presenter.MoneyDeatail(null,null,null)

//
//        presenter.TakeOut()
    }
}
