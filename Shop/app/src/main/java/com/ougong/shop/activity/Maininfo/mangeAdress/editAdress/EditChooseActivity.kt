package com.ougong.shop.activity.Maininfo.mangeAdress.editAdress

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.R
import com.ougong.shop.activity.Maininfo.mangeAdress.AddressBean
import com.ougong.shop.activity.Maininfo.mangeAdress.ChooseAddress.ChoosePlaceContract
import com.ougong.shop.activity.Maininfo.mangeAdress.ChooseAddress.ChoosePlacePresenter
import com.ougong.shop.activity.Maininfo.mangeAdress.addAddressdatabaseBean
import com.ougong.shop.base_mvp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_choose_place.*

class EditChooseActivity : BaseActivity<ChoosePlaceContract.view, ChoosePlaceContract.presenter>(), ChoosePlaceContract.view,
    onitemclickLineter {

    var bean: fuckNetbean<addAddressdatabaseBean>? = null

    var chooseStep = 1

    override fun GetDataSucess(it: fuckNetbean<addAddressdatabaseBean>) {
        adapter.setData(it.data)
        bean = it
        for (index in bean!!.data.indices) {
            println(bean!!.data[index])
            if (bean!!.data[index].id == mSelectProviceId){
                mSelectProvicePosition = index
            }
        }
        for (index in bean!!.data[mSelectProvicePosition].children.indices) {
            println(bean!!.data[index])
            if (bean!!.data[mSelectProvicePosition].children[index].id == mSelectCityId){
                mSelectCityId = index
            }
        }

        for (index in bean!!.data[mSelectProvicePosition].children[mSelectCityPosition].children.indices) {
            println(bean!!.data[index])
            if (bean!!.data[mSelectProvicePosition].children[mSelectCityPosition].children[index].id == mSelectProviceId){
                mSelectProvicePosition = index
            }
        }

        tlTabLayout.getTabAt(0)?.select()
    }

    override fun onitemClick(position: Int) {

    }

    override fun setContentViewSource() = R.layout.activity_choose_place

    override fun initPresenter() = ChoosePlacePresenter()

    var adapter = chooseAdapter()

    override fun initData() {
        val b = intent.getSerializableExtra("address") as AddressBean
        mSelectProviceId = b.provinceId
        mSelectCityId =  b.cityId
        mSelectDistrictId = b.districtId
    }
    override fun initView() {


        val m = window.windowManager
        val d = m.defaultDisplay
        val p = window.attributes
        p.width = d.width
        window.attributes = p


        rvList.adapter = adapter
        adapter.onitemclickLineter = this
        rvList.layoutManager = LinearLayoutManager(this)

        tlTabLayout.addTab(tlTabLayout.newTab().setText("选择省份"))


        tlTabLayout.addTab(tlTabLayout.newTab().setText("城市"))


        tlTabLayout.addTab(tlTabLayout.newTab().setText("区县"))

        presenter.getData()

        tlTabLayout.addOnTabSelectedListener(tabSelectedListener)

        yes.setOnClickListener {

            val intent = Intent()

            if (mSelectProvicePosition == -1){
                ToastUtils.toast(this,"请选择地区")
                return@setOnClickListener
            }

            if (mSelectCityPosition == -1){
                ToastUtils.toast(this,"请选择地区")

                return@setOnClickListener
            }

            if (mSelectDistrictPosition == -1){
                ToastUtils.toast(this,"请选择地区")

                return@setOnClickListener
            }
            if (bean == null)
                return@setOnClickListener
            intent.putExtra("data1",bean!!.data[mSelectProvicePosition].id)
            intent.putExtra("data2",bean!!.data[mSelectProvicePosition].children[mSelectCityPosition].id)
            intent.putExtra("data3",bean!!.data[mSelectProvicePosition].children[mSelectCityPosition].children[mSelectDistrictPosition].id)

            intent.putExtra("data4",bean!!.data[mSelectProvicePosition].name+bean!!.data[mSelectProvicePosition].children[mSelectCityPosition].name+
                    bean!!.data[mSelectProvicePosition].children[mSelectCityPosition].children[mSelectDistrictPosition].name
            )

            setResult(223,intent)
            finish()
        }
    }

    private var mSelectProviceId = -1 //选中 省份 位置
    private var mSelectCityId = -1//选中 城市  位置
    private var mSelectDistrictId = -1//选中 区县  位置


    private var mSelectProvicePosition = -1 //选中 省份 位置
    private var mSelectCityPosition = -1//选中 城市  位置
    private var mSelectDistrictPosition = -1//选中 区县  位置


    /**
     * TabLayout 切换事件
     */
    internal var tabSelectedListener: TabLayout.OnTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            when (tab.position) {
                0 -> {
                    if (bean != null) {
                        adapter.setData(bean!!.data)

                        adapter.notifyDataSetChanged()
                        // 滚动到这个位置
                        if (mSelectProvicePosition != -1) {
                            rvList.smoothScrollToPosition(mSelectProvicePosition)
                        }
                    }
                }
                1 -> {

                    if (bean != null) {
                        if (mSelectProvicePosition != -1) {
                            adapter.setData(bean!!.data[mSelectProvicePosition].children)
                        } else {
                            Toast.makeText(this@EditChooseActivity, "请您先选择省份", Toast.LENGTH_SHORT).show()
                            return
                        }
                        adapter.notifyDataSetChanged()
                        // 滚动到这个位置
                        if (mSelectCityPosition != -1)
                            rvList.smoothScrollToPosition(mSelectProvicePosition)

                    }
                }
                2 -> {

                    if (bean != null) {
                        if (mSelectCityPosition != -1) {
                            adapter.setData(bean!!.data[mSelectProvicePosition].children[mSelectCityPosition].children)
                        } else {
                            Toast.makeText(this@EditChooseActivity, "请您先选择城市", Toast.LENGTH_SHORT).show()
                            return
                        }
                        adapter.notifyDataSetChanged()
                        // 滚动到这个位置
                        if (mSelectDistrictPosition != -1)
                            rvList.smoothScrollToPosition(mSelectDistrictPosition)

                    }
                }
            }


        }

        override fun onTabUnselected(tab: TabLayout.Tab) {}

        override fun onTabReselected(tab: TabLayout.Tab) {}
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        finish()
        return true
    }

    inner class chooseAdapter : RecyclerView.Adapter<chooseAdapter.VH>() {

        var onitemclickLineter: onitemclickLineter? = null
        //            set(value) {
//                field = value
//            }
        var mList: Array<addAddressdatabaseBean>? = null

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VH {
            var textview = TextureView(p0.context)
            return VH(LayoutInflater.from(p0.context).inflate(R.layout.choose_text, p0, false))
        }

        override fun getItemCount(): Int {
            if (null == mList) {
                return 0
            } else {
                return mList!!.size
            }

        }

        override fun onBindViewHolder(p0: VH, p1: Int) {

            (p0.view as TextView).setText(mList!![p1].name)
            p0.view.setOnClickListener {

                when (tlTabLayout.selectedTabPosition) {
                    0 -> {
                        mSelectProvicePosition = p1
                        tlTabLayout.getTabAt(0)?.setText(mList!![p1].name)
                        tlTabLayout.getTabAt(1)?.select()
                    }
                    1 -> {

                        mSelectCityPosition = p1
                        tlTabLayout.getTabAt(1)?.setText(mList!![p1].name)
                        tlTabLayout.getTabAt(2)?.select()
                    }

                    else -> {

                        mSelectDistrictPosition = p1
                        tlTabLayout.getTabAt(2)?.setText(mList!![p1].name)
                        //todo finsh
                    }
                }

                onitemclickLineter?.onitemClick(p1)
            }
        }

        fun setData(list: Array<addAddressdatabaseBean>) {

            mList = list
            notifyDataSetChanged()

        }


        inner class VH(var view: View) : RecyclerView.ViewHolder(view)
    }

}
//
//    override fun onitemClick(position: Int){
//
//    }

interface onitemclickLineter {
    fun onitemClick(position: Int)
}