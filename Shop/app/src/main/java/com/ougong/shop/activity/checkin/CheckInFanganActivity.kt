package com.ougong.shop.activity.checkin

import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.baigui.commonlib.kotlinUtils.extensions.Fromate
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.AccountHelper
import com.ougong.shop.Bean.CateryBean
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.SxTypeBean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.MsgContract
import com.ougong.shop.R
import com.ougong.shop.activity.Account.login.LogInActivity
import com.ougong.shop.base_mvp.base.BaseActivity
import com.ougong.shop.httpmodule.HxSpaceBean
import com.ougong.shop.httpmodule.ProductCheckLinBean
import com.ougong.shop.utils.MessageBus
import com.ougong.shop.utils.MessageBusMessage
import com.ougong.shop.utils.Subscrition
import io.armcha.ribble.presentation.utils.extensions.start
import kotlinx.android.synthetic.main.activity_check_in_fangan.*
import kotlinx.android.synthetic.main.include_back_title.*

class CheckInFanganActivity : BaseActivity<CheckInFanganContract.View,CheckInFanganContract.Presenter>(),CheckInFanganContract.View,
    View.OnClickListener {
    override fun requestSucessData(bean: Netbean<String>) {
        //TODO 清空拎包入住缓存
        ToastUtils.toast(this,"方案提交成功")
        CheckLinUtils.getInstance().clear()
    }

    override fun initHouseSpace(bean: fuckNetbean<HxSpaceBean>) {
        val names = arrayListOf<HxSpaceBean>()
        for (hxSpace in bean.data){
            names.add(hxSpace)
        }
        CheckLinUtils.getInstance().checkHxNames = names
        presenter.getAllList()
    }

    override fun initCategory(bean: fuckNetbean<CateryBean>) {
        //TODO 1.初始化缓存分类信息列表  2. 将缓存分类信息列表的数据给到gv_fenlei显示
        CheckLinUtils.getInstance().initRoomCateAndProduct(bean.data[0].child)
        initTabLayout(CheckLinUtils.getInstance().checkHxNames)
    }


    var hxId:Int? = 0

    override fun setContentViewSource() = R.layout.activity_check_in_fangan
    override fun initPresenter() = CheckInFanganPresenter()

    companion object {
        val FANGAN_ID: String = "FANGAN_ID"
    }


    override fun initView() {
        super.initView()
        MessageBus.getBus().register(this)
        hxId = intent.getIntExtra(FANGAN_ID,0)
        title_name.text = CheckLinUtils.getInstance().checkLinName
        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener {
            finish()
        }
        ll_sp_mine.setOnClickListener(this)
        ll_sc.setOnClickListener(this)
        ll_fa_insert.setOnClickListener(this)
        ll_fa.setOnClickListener(this)
        tv_commit_fa.setOnClickListener(this)
        tv_edit.setOnClickListener(this)

        presenter.getHouseSpace(hxId)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ll_sp_mine -> {   //我的配置单
                val dialog = MyProfileDialog()
                dialog.show(supportFragmentManager,"MyProfileDialog")
            }
            R.id.ll_sc -> {        //收藏
                presenter.shoucang(CheckLinUtils.getInstance().requestBody)
            }
            R.id.ll_fa_insert -> { //创建方案
                start<CheckInActivity>()
            }
            R.id.ll_fa -> {        //我的方案
                start<MyFanganActivity>()
            }
            R.id.tv_commit_fa -> {  //提交方案
                if (AccountHelper.isLogin) {
                    commitCheckLin()
                }else{
                    ToastUtils.toast(this,"请先登录后再操作")
                    start<LogInActivity>()
                }
            }
            R.id.tv_edit -> {       //编辑布局
                val dialog = EditorDialog()
                dialog.show(supportFragmentManager,"EditorDialog")
            }
        }

    }

    private fun commitCheckLin() {
        presenter.saveCheckLinFangan(CheckLinUtils.getInstance().requestBody)
    }

    fun updateProductSpec(specifications: Array<ProductCheckLinBean.SpecDataBean>){
        (viewpager.adapter as TitleAdapter).frags.get(viewpager.currentItem).updateSpecData(specifications)
    }
    fun initTabLayout(hxData: List<HxSpaceBean>) {
        viewpager.adapter = TitleAdapter(supportFragmentManager).setData(hxData)
        tabLayout.setupWithViewPager(viewpager)

        for(i in 0 ..(tabLayout.tabCount-1)){
            val tabView = LayoutInflater.from(this).inflate(R.layout.tab_text, null) as TextView
            tabView.text = (viewpager.adapter as TitleAdapter).getPageTitle(i)
            tabLayout.getTabAt(i)!!.customView = tabView
            tabView.setOnClickListener {
                tabLayout.getTabAt(i)?.select()
                (viewpager.adapter as TitleAdapter).frags[i].initGridView()
                for (j in 0 .. (tabLayout.tabCount-1)){
                    (tabLayout.getTabAt(j)!!.customView as TextView).setTextColor(Color.parseColor("#999999"))
                }
                (tabLayout.getTabAt(i)!!.customView as TextView).setTextColor(Color.BLACK)
            }
        }
        if(tabLayout.tabCount>=1){
            tabLayout.getTabAt(0)?.select()
            (tabLayout.getTabAt(0)!!.customView as TextView).setTextColor(Color.BLACK)
        }
    }

    class TitleAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm){
        var hxData:ArrayList<HxSpaceBean>? = arrayListOf()
        var frags :ArrayList<CheckInFanganFragment> = arrayListOf()

        override fun getItem(p0: Int): Fragment {
            return frags[p0]
        }

        override fun getCount(): Int {
            return frags.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return hxData!![position].name
        }

        fun setData(hxData: List<HxSpaceBean>): TitleAdapter? {
            this.hxData?.clear()
            this.hxData?.addAll(hxData)
            for (hxInt in 0..(hxData.size -1)){
                frags.add(CheckInFanganFragment().setData(hx = hxData[hxInt],roomId =hxInt, roomName = hxData[hxInt].name!!))
            }
            return this
        }
    }
    @Subscrition(action = MsgContract.RESH_CHECKLIN_ROOM_CATEGORY,threadMode = Subscrition.ThreadMode.MAIN)
    fun onEventMessage(msg:MessageBusMessage){
        val mCurrent:Int = msg.data as Int
        (viewpager.adapter as TitleAdapter).frags.get(mCurrent).reshGv()
    }

    @Subscrition(action = MsgContract.RESH_CHECKLIN_ROOM_NUM_PRICE,threadMode = Subscrition.ThreadMode.MAIN)
    fun onEventMessage1(msg:MessageBusMessage){
        var count = 0
        var price = 0.0
        val myProfile = CheckLinUtils.getInstance().myProfile
        for (roomIndex in 0..(myProfile.length() - 1)){
            val room = myProfile.getJSONObject(roomIndex)
            val category = room.getJSONArray("category")
            for(cateIndex in 0..(category.length() - 1)){
                val products = category.getJSONObject(cateIndex)
                val pro = products.getJSONArray("products")
                for (proIndex in 0..(pro.length() - 1)){
                    val product = pro.getJSONObject(proIndex)
                    count += product.getInt("count")
                    price += product.getDouble("price")
                }
            }
        }
        tv_priceccccc.text = "全部房间："+price.Fromate()
        tv_commit_fa.text = "提交方案("+count+")"
    }

    @Subscrition(action = MsgContract.RESH_CHECKLIN_PRODUCT_SX,threadMode = Subscrition.ThreadMode.MAIN)
    fun onEventMessage4(msg:MessageBusMessage){
        val bean = msg.data as SxTypeBean
        (viewpager.adapter as TitleAdapter).frags.get(viewpager.currentItem).reshProductData(bean)

    }
}