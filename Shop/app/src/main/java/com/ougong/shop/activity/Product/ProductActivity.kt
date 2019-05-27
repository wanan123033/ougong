package com.ougong.shop.activity.Product

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.TextUtils
import android.view.View
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.baigui.commonlib.kotlinUtils.WebViewUtil
import com.baigui.commonlib.kotlinUtils.extensions.Fromate
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ToastUtils
import com.bumptech.glide.Glide
import com.ougong.shop.AccountHelper
import com.ougong.shop.App
import com.ougong.shop.Bean.*
import com.ougong.shop.ConstString
import com.ougong.shop.ConstString.*
import com.ougong.shop.R
import com.ougong.shop.activity.Account.login.LogInActivity
import com.ougong.shop.activity.Order.QuickOrderActivity
import com.ougong.shop.activity.Product.ChoosePropertoes.ChooseProPertoseActivity
import com.ougong.shop.activity.Product.ProductDetail.ProductDetailActivity
import com.ougong.shop.activity.ShopCar.ShopCarActivity
import com.ougong.shop.utils.GlideImageLoader
import com.ougong.shop.base_mvp.base.BaseActivity
import com.ougong.shop.mtest1.TestFrament
import io.armcha.ribble.presentation.utils.extensions.start
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.title_group_item.view.*

class ProductActivity : BaseActivity<ProductContract.View, ProductContract.Presenter>(), ProductContract.View {
    var productDetailKeyValue : fuckProcudtList<productDetailKeyValue>? = null
    override fun getDetailSucess(data: fuckProcudtList<productDetailKeyValue>) {
        productDetailKeyValue = data
        initProper()
    }

    override fun getProperSucess(data: fuckProcudtList<productSpec>) {
        if (mSelectProductSpec != null) {
            LogUtils.e("这不可能发生", this)
        }
        if (data.rows.size == 0){
            return
        }
        mSelectProductSpec = data.rows[0]

        mSelectProductSpec!!.appCount = 1
        mIsOnlySpec = data.rows.size == 1
        presenter.getDetails(mSelectProductSpec!!.id)
        initProper()
    }

    var mIsOnlySpec = false

    override fun onCreate(savedInstanceState: Bundle?) {
        StarusBarUtils.transparencyBar(this)
        StarusBarUtils.setAndroidNativeLightStatusBar(this, true)
        super.onCreate(savedInstanceState)
    }

    override fun addSucess() {
        ToastUtils.toast(this, "加入购物车成功")
        //todo 购物车动画
        AccountHelper.globalDataTemp.shopCarCount =
            AccountHelper.globalDataTemp.shopCarCount + (mSelectProductSpec?.appCount ?: 0)
        refeshView()
    }

    override fun setContentViewSource() = R.layout.activity_product

    override fun initPresenter() = ProductPresenter()

    var productBean: ProductDetails? = null

    override fun getProductSucess(it: Netbean<ProductDetails>) {
        productBean = it.data
        val list = ArrayList<String>(3)
        list.add(it.data.headImage)
        list.addAll(it.data.specImages)
        banner.setImages(list)
            .setImageLoader(GlideImageLoader())
            .start()

        title_name.setText(it.data.title)

        presenter.getProper(it.data.id)
        Glide.with(App.app!!)
            .load(it.data.brand.logo)
            .error(Glide.with(this).load(R.drawable.catery_title_default))
            .into(brand_ice)
        brand_name.text = it.data.brand.name
        if(TextUtils.isEmpty(it.data.details)){
            webview.visibility = View.GONE
            no_web_view.visibility = View.VISIBLE
        }else{
            no_web_view.visibility = View.GONE
            webview.visibility = View.VISIBLE
            webview.loadDataWithBaseURL(null, WebViewUtil.getNewContent(it.data.details), "text/html", "utf-8", null);
        }
        webview.loadDataWithBaseURL(null, WebViewUtil.getNewContent(it.data.details), "text/html", "utf-8", null);
//        metral.setText()

         }

    var mBean: ProductListItem? = null
    override fun initData() {
        mBean = intent.getSerializableExtra(PRODUCT_ITEM) as ProductListItem
//        mBean!!.id = 3947

        presenter.getProductDetails(mBean!!.id)
    }

    fun refeshView() {
        if (AccountHelper.globalDataTemp.shopCarCount > 0) {
            dot_shopcar_count.visibility = View.VISIBLE
            dot_shopcar_count.text = AccountHelper.globalDataTemp.shopCarCount.toString()
        } else {
            dot_shopcar_count.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        refeshView()
    }
    override fun initView() {
        refeshView()
        shopcar_container.setOnClickListener {
            start<ShopCarActivity>()
        }

        market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG)
        details_Lin.setOnClickListener {

            /**
             * 这个现在基本永远不为空
             */
            mSelectProductSpec?.let {

                val intent = Intent(this, ProductDetailActivity::class.java)
                intent.putExtra("id", mSelectProductSpec!!.id)
                startActivity(intent)

            } ?: ToastUtils.toast(this, "请先选择规格")
        }

        add_shopcar.setOnClickListener {
            if (!AccountHelper.isLogin){
                goToLogIn()
                return@setOnClickListener
            }
            if (productBean == null) {
                return@setOnClickListener
            }
            if (mIsOnlySpec && mIsHumanChoose) {
                /**
                 * 现在是 只能一个商品并且已经选择的，就可以直接添加购物车。数量就是默认的
                 */
                presenter.addShopCar(mSelectProductSpec!!.id, mSelectProductSpec!!.appCount)
            } else {

                //没有选择规格，就不能加入购物车，先去选择规格
                val intent = Intent(this, ChooseProPertoseActivity::class.java)
                intent.putExtra("id", productBean!!.id)
                intent.putExtra("name", productBean!!.title)
                intent.putExtra("headImage", productBean!!.headImage)
                intent.putExtra("selectProduct",mSelectProductSpec)

                startActivityForResult(intent, REQUEST_CODE_CHOOSE_SPEC)

            }
        }

        buy_it.setOnClickListener {
            if (!AccountHelper.isLogin) {
                ToastUtils.toast(this, "您未登陆")
                //todo 登录
                goToLogIn()
                return@setOnClickListener
            }
            if (productBean == null) {
                return@setOnClickListener
            }

            if (mSelectProductSpec == null) {
                LogUtils.e("未选择规格", this)
            }
            /**
             * 默认不会发生
             */
            mSelectProductSpec?.let {
                val intent = Intent(this, QuickOrderActivity::class.java)
                intent.putExtra("order", mSelectProductSpec!!.id.toString() + "-" + mSelectProductSpec!!.appCount)
                startActivity(intent)
            }
//
//            if (mSelectProductSpec) {
//
//                ToastUtils.toast(this, "请先选择规格")
//                //没有选择规格，就不能加入购物车，先去选择规格
//                val intent = Intent(this, ChooseProPertoseActivity::class.java)
//                intent.putExtra("id", productBean!!.id)
//                intent.putExtra("name", productBean!!.title)
//                intent.putExtra("headImage", productBean!!.headImage)
//                intent.putExtra("selectProduct",mSelectProductSpec)
//
//                startActivityForResult(intent, REQUEST_CODE_CHOOSE_SPEC)
//            } else {
//            }
        }
        back.setOnClickListener { finish() }
        spec_Lin.setOnClickListener {
            if (productBean == null || mSelectProductSpec == null) {
                return@setOnClickListener
            }
            val intent = Intent(this, ChooseProPertoseActivity::class.java)
            intent.putExtra("id", productBean!!.id)
            intent.putExtra("name", productBean!!.title)
            intent.putExtra("selectProduct",mSelectProductSpec)
            //
            //           intent.putExtra("left",productBean!!.)
            startActivityForResult(intent, REQUEST_CODE_CHOOSE_SPEC)
        }
    }

    private fun goToLogIn() {
        startActivityForResult(Intent(this, LogInActivity::class.java), ConstString.LOG_IN_REQUEST)
    }

    var mIsHumanChoose = false
    var mSelectProductSpec: productSpec? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_CHOOSE_SPEC -> {
                if (resultCode == RESULT_CODE_OK) {
                    data?.let {
                        mIsHumanChoose = true
                        mSelectProductSpec = data!!.getSerializableExtra("product") as productSpec

                        presenter.getDetails(mSelectProductSpec!!.id)
                        initProper()
                    }
                }
            }
            LOG_IN_REQUEST ->{
                if (resultCode == LOG_IN_RESULT_OK){
                    //todo  记忆前一次的  操作  继续操作
                }
            }
        }
    }

    private fun initProper() {
        show_price.setText((mSelectProductSpec!!.price).Fromate())
        market_price.setText(""+(mSelectProductSpec!!.marketPrice).Fromate())

        productDetailKeyValue?.let {
            if(productDetailKeyValue!!.rows.size == 0)
                return
            productDetail.text =  productDetailKeyValue!!.rows[0].name + "   "+ productDetailKeyValue!!.rows[0].value
        }
        color.text = mSelectProductSpec!!.color +"   "+ mSelectProductSpec!!.spec

    }
}
