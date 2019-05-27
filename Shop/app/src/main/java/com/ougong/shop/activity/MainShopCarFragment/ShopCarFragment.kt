package com.ougong.shop.activity.MainShopCarFragment

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.baigui.commonlib.kotlinUtils.extensions.Fromate
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ToastUtils
import com.ougong.shop.AccountHelper
import com.ougong.shop.Bean.BrandList.Companion.CHECK
import com.ougong.shop.Bean.BrandList.Companion.CHEKED
import com.ougong.shop.Bean.FuckData
import com.ougong.shop.Bean.SpecificationCartVos
import com.ougong.shop.R
import com.ougong.shop.activity.Order.OrderActivity
import com.ougong.shop.activity.ShopCar.ShopCarActivity
import com.ougong.shop.activity.orderpay.QuickOrderActivity
import com.ougong.shop.base_mvp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_shop_car.*
import kotlinx.android.synthetic.main.include_shopcar_title.*
import java.lang.StringBuilder

class ShopCarFragment : BaseFragment<ShopCarContract.View, ShopCarContract.Presenter>(), ShopCarContract.View {
    override fun DelectSucess() {
        AccountHelper.synchronizationShopCar()
        setEditMode(true)
    }

    var mBean: FuckData? = null
    override fun getShopCarList(data: FuckData) {
        if (data.brandList == null) {
            title.text = "购物车("+0+")"
            showemppty(true)
            return
        }
        if (data.brandList!!.isEmpty()) {
            title.text = "购物车("+0+")"
            showemppty(true)
            return
        }

        mBean = data
        mShopCarAdapter.setData(data)
        mShopCarAdapter.notifyDataSetChanged()

        title.text = "购物车("+(mShopCarAdapter.itemCount-data.brandList!!.size)+")"

    }

    private fun showemppty(b: Boolean) {
        if (b) {
            recycleView.visibility = View.GONE
            bottom_bar.visibility = View.GONE
            empty_view.visibility = View.VISIBLE
        } else {
            recycleView.visibility = View.VISIBLE
            bottom_bar.visibility = View.VISIBLE
            empty_view.visibility = View.GONE
        }
    }

    override fun initPresenter() = ShopCarPresenter()

    override fun setContentViewSource() = R.layout.fragment_shop_car

    var mIsedit = false

    private lateinit var mShopCarAdapter: ShopCarAdapter


    //    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//    }
    fun refesh() {
        var itemcount = 0
        var totalPrice = 0.toDouble() // 都给算出来吧
        for (it in mBean!!.brandList!!) {
            for (item in it.specificationCartVos!!) {
                if (item.appCheckState == CHEKED) {
                    itemcount++
                    totalPrice = totalPrice + item.price * item.count
                }
            }
        }
        if (mIsedit) {
            total_price.visibility = View.GONE
            edit_check_all.isChecked = mBean?.isAllSelect ?: false
            shopcar_delect.setText("删除（" + itemcount + ")")
        } else {
            if (itemcount == 0) {
                total_price.visibility = View.GONE
            } else {
                total_price.visibility = View.VISIBLE
            }
            check_all.isChecked = mBean?.isAllSelect ?: false
            total_price.text = "合计: " + totalPrice.Fromate()
            buy_it.setText("去结算（" + itemcount + ")")
        }
    }

    override fun initView() {

        if (activity is ShopCarActivity) {
            title_back.visibility = View.VISIBLE
            title_back.setOnClickListener { activity.finish() }
        }

        showemppty(false)
        mShopCarAdapter = ShopCarAdapter(activity as Context)

        mShopCarAdapter.setOnSelect(object : OnSelectChange {
            override fun onchange() {
                refesh()
            }

            override fun onChangeNumber(item: SpecificationCartVos, isPluse: Boolean) {
                presenter.ChangeShopCarNO(item.count, item.id)
                refesh()
            }
        })

        shopcar_delect.setOnClickListener {

            LogUtils.e("" + getDelerctList().size, this);
            presenter.DelectShopCar(getDelerctList())
        }

        check_all.setOnClickListener {

            setSelect(check_all.isChecked)
            mShopCarAdapter.notifyDataSetChanged()
            //这个其实应该在这里做，但是万恶的设计模式啊
            refesh()
        }


        edit_check_all.setOnClickListener {
            setSelect(edit_check_all.isChecked)
            mShopCarAdapter.notifyDataSetChanged()
            refesh()
        }

//        edit_check_all.setOnClickListener {
//
//            setSelect(check_all.isChecked)
//            mShopCarAdapter.notifyDataSetChanged()
//            //这个其实应该在这里做，但是万恶的设计模式啊
//            refesh()
//        }
        recycleView.setAdapter(mShopCarAdapter)
        //这里不用自定义的流式布局也是可以的，这里这是根据特定需要简单自定义了一个
        //    recycleView.setLayoutManager(StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL))

        recycleView.layoutManager = LinearLayoutManager(activity)
        title_left.setOnClickListener {
            if (mBean == null)
                return@setOnClickListener
            mIsedit = !mIsedit
            setEditMode(mIsedit)
        }

        buy_it.setOnClickListener {

            val intent = Intent(activity, QuickOrderActivity::class.java)
            val orderstring = getBuyString()
            if (TextUtils.isEmpty(orderstring)) {
                ToastUtils.toast(activity, "请选择商品")
                return@setOnClickListener
            }
            intent.putExtra(QuickOrderActivity.ORDER_DATA, getBuyString())
            intent.putExtra(QuickOrderActivity.ORDER_TYPE,1)
            intent.putExtra(QuickOrderActivity.ORDER_STR_CONTENT,getProductString())
            startActivity(intent)
//
        }

        setEditMode(mIsedit)
    }

    private fun getBuyString(): String {
        val tem = StringBuilder()
        for (it in mBean!!.brandList!!) {
            for (item in it.specificationCartVos!!) {
                if (item.appCheckState == CHEKED) {
                    if (tem.isEmpty()) {
                        tem.append(item.id.toString() + "-" + item.count)
                    } else {
                        tem.append("," + item.id.toString() + "-" + item.count)
                    }
                }
            }
        }
        return tem.toString()
    }
    private fun getProductString(): String {
        val tem = StringBuilder()
        for (it in mBean!!.brandList!!) {
            for (item in it.specificationCartVos!!) {
                if (item.appCheckState == CHEKED) {
                    if (tem.isEmpty()) {
                        tem.append(item.properties!![0].specId.toString() + "-" + item.count)
                    } else {
                        tem.append("," + item.properties!![0].specId.toString() + "-" + item.count)
                    }
                }
            }
        }
        return tem.toString()
    }

    fun getDelerctList(): ArrayList<Int> {
        var tem = arrayListOf<Int>()
        for (it in mBean!!.brandList!!) {
            for (item in it.specificationCartVos!!) {
                if (item.appCheckState == CHEKED) {
                    tem.add(item.id)
                }
            }
        }
        return tem
    }


    private fun setEditMode(mIsedit: Boolean) {
        if (mIsedit) {
            title_left.text = "完成"
            check_all.visibility = View.GONE
            check_all_txt.visibility = View.GONE
            total_price.visibility = View.GONE
            buy_it.visibility = View.GONE

            shopcar_delect.setText("删除（0）")
            edit_check_all.visibility = View.VISIBLE
            edit_check_all_txt.visibility = View.VISIBLE
//            shopcar_collect.visibility = View.VISIBLE
//            shopcar_share.visibility = View.VISIBLE
            shopcar_delect.visibility = View.VISIBLE
        } else {

            edit_check_all.visibility = View.GONE
            edit_check_all_txt.visibility = View.GONE
//            shopcar_collect.visibility = View.GONE
//            shopcar_share.visibility = View.GONE
            shopcar_delect.visibility = View.GONE

            buy_it.setText("去结算（0）")
            title_left.text = "编辑"
            check_all.visibility = View.VISIBLE
            check_all_txt.visibility = View.VISIBLE
            total_price.visibility = View.VISIBLE
            buy_it.visibility = View.VISIBLE
        }
        check_all.isChecked = false
        edit_check_all.isChecked = false
        setSelect(false)

    }

    private fun setSelect(isCheck: Boolean) {
        if (mBean == null) {
            return
        }
        if (mBean!!.brandList == null) {
            return
        }
        var state = CHECK
        if (isCheck) {
            state = CHEKED
        }

        for (it in mBean!!.brandList!!) {
            it.appCheckState = state
            for (item in it.specificationCartVos!!) {
                item.appCheckState = state
            }
        }

        mShopCarAdapter.notifyDataSetChanged()
        //更新全选状态
        mShopCarAdapter.refeshDataFromeParent()
    }

    override fun initData() {

        if (!AccountHelper.isLogin) {
            GotoLogin()
        }
    }

    override fun onResume() {
        super.onResume()
        title.text = "购物车("+AccountHelper.globalDataTemp.shopCarCount+")"
        presenter.getShopCarList()
        check_all.isChecked = false
        edit_check_all.isChecked = false
    }
}