package com.ougong.shop.activity.Product.ChoosePropertoes

import android.app.Activity
import android.content.Intent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import android.widget.TextView
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.baigui.commonlib.kotlinUtils.extensions.Fromate
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ToastUtils
import com.bumptech.glide.Glide
import com.ougong.shop.AccountHelper
import com.ougong.shop.App
import com.ougong.shop.Bean.fuckProcudtList
import com.ougong.shop.Bean.productSpec
import com.ougong.shop.ConstString
import com.ougong.shop.ConstString.RESULT_CODE_FAIL
import com.ougong.shop.ConstString.RESULT_CODE_OK
import com.ougong.shop.R
import com.ougong.shop.activity.Order.QuickOrderActivity
import com.ougong.shop.base_mvp.base.BaseActivity
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import io.armcha.ribble.presentation.utils.extensions.get
import kotlinx.android.synthetic.main.activity_choose_pro_pertose.*

class ChooseProPertoseActivity : BaseActivity<ChoosePropertoesContract.View, ChoosePropertoesContract.Presenter>(),
    ChoosePropertoesContract.View {
    override fun addSucess() {
        ToastUtils.toast(this, "加入购物车成功")
        AccountHelper.globalDataTemp.shopCarCount =
            AccountHelper.globalDataTemp.shopCarCount + Integer.parseInt(numuber.text.toString())
        finish()
    }

    var clolor = arrayListOf<String>()

    var spec = arrayListOf<String>()

    var productSpecList: Array<productSpec>? = null
    override fun getSucess(data: fuckProcudtList<productSpec>) {

        productSpecList = data.rows

        for (it in data.rows) {
            var col = false
            for (item: String in clolor) {
                if (item.equals(it.color)) {
                    col = true
                    break
                } else {
                    continue
                }
            }
            if (!col) {
                clolor.add(it.color)
            }
            var iSspec = false
            for (item: String in spec) {
                if (item.equals(it.spec)) {
                    iSspec = true
                    break
                } else {
                    continue
                }
            }
            if (!iSspec) {
                spec.add(it.spec)
            }

            if (it.isDefault)
                default = it
        }

        id_flowlayout.adapter = object : TagAdapter<String>(clolor) {

            override fun getView(parent: FlowLayout, position: Int, s: String): View {
                val tv = layoutInflater.inflate(
                    R.layout.flowview_text,
                    id_flowlayout, false
                ) as TextView
                tv.text = s
                return tv
            }
        }

        id_flowlayout1.adapter = object : TagAdapter<String>(spec) {

            override fun getView(parent: FlowLayout, position: Int, s: String): View {
                val tv = layoutInflater.inflate(
                    R.layout.flowview_text,
                    id_flowlayout1, false
                ) as TextView
                tv.text = s
                return tv
            }
        }

        if (mInitProduct == null) {
            id_flowlayout.get(0).performClick()
            id_flowlayout1.get(0).performClick()

        } else {
            id_flowlayout.get(clolor.indexOf(mInitProduct!!.color)).performClick()
            id_flowlayout1.get(spec.indexOf(mInitProduct!!.spec)).performClick()
        }
    }


    var default: productSpec? = null

    var current: productSpec? = null

    override fun setContentViewSource() = R.layout.activity_choose_pro_pertose

    override fun initPresenter() = ChooseProPerPresenter()

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        resulutData()
        finish()
        return true
    }

    fun resulutData(){

        var i = Intent()
        if (!id_flowlayout.selectedList.isEmpty() && !id_flowlayout1.selectedList.isEmpty()) {
            i.putExtra("product", getProduct())
            setResult(RESULT_CODE_OK, i)
        } else {
            setResult(RESULT_CODE_FAIL, i)
        }
    }

    override fun onDestroy() {
        var i = Intent()
        i.putExtra("product", getProduct())
        setResult(RESULT_CODE_OK, i)
        super.onDestroy()
    }

    var id: Int = 1
    var mInitProduct: productSpec? = null
    override fun initData() {
        id = intent.getIntExtra("id", 0)
        presenter.getProper(id)

        mInitProduct = intent.getSerializableExtra("selectProduct") as productSpec
        if (mInitProduct == null) {
            return
        }
        name.text = intent.getStringExtra("name")

        if (mInitProduct?.images?.size == 0) {
            //todo
        } else {
            Glide.with(App.app!!)
                .load(mInitProduct?.images?.get(0))
                .error(Glide.with(this).load(R.drawable.catery_child_default))
                .into(image)
        }
        price.text = (mInitProduct!!.price * mInitProduct!!.appCount).Fromate()
        left.text = "库存: " + mInitProduct?.stock
        numuber.text = "" + mInitProduct?.appCount
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ConstString.LOG_IN_REQUEST) {
            if (resultCode == ConstString.LOG_IN_RESULT_OK) {//最好是刷新一下Fragment。但是每次解锁屏幕都要请求数据，用最暴力的方法解决吧

            } else if (resultCode == ConstString.LOG_IN_RESULT_NO) {

                finish()
//                LogUtils.e("------------------------",this)
//                mHomePageFragment = doSelect(mHomePageFragment,0){homePageFragmentV2()} }
            }
        }
    }

    var isCheckProduct = false
    override fun initView() {
        StarusBarUtils.transparencyBar(this)
        StarusBarUtils.setAndroidNativeLightStatusBar(this, true)
        container.setOnClickListener {  }
        minus.setOnClickListener {
            var i = Integer.parseInt(numuber.text.toString())
            if (i > 1) {
                numuber.setText("" + (i - 1))
                refeshView()
            } else {
                ToastUtils.toast(this, "请选择大于一件商品")
            }
        }


        pluse.setOnClickListener {
            val i = Integer.parseInt(numuber.text.toString())

            if (i >= 99) {
                ToastUtils.toast(this, "商品不能多于99件")
                return@setOnClickListener
            }

            if (i >= getProduct().stock) {
                ToastUtils.toast(this, "商品不能多于库存")
                return@setOnClickListener
            }
            numuber.setText("" + (i + 1))
            refeshView()
        }

        bug_it.setOnClickListener {

            if (!AccountHelper.isLogin) {
                ToastUtils.toast(this, "您未登陆")
                GotoLogin()
                return@setOnClickListener
            }

            if (id_flowlayout.selectedList.isEmpty()) {
                ToastUtils.toast(this, "请选择颜色")
                return@setOnClickListener
            }

            if (id_flowlayout1.selectedList.isEmpty()) {
                ToastUtils.toast(this, "请选择规格")
                return@setOnClickListener
            }

            val i = Integer.parseInt(numuber.text.toString())

//            presenter.addShopCar(getProduct().id,i)


            val intent = Intent(this, QuickOrderActivity::class.java)
            intent.putExtra("order", getProduct().id.toString() + "-" + i)
            startActivity(intent)
        }

        close_window.setOnClickListener { resulutData()
            finish() }
        add_shopcar.setOnClickListener {

            if (!AccountHelper.isLogin) {
                ToastUtils.toast(this, "您未登陆")
                GotoLogin()
                return@setOnClickListener

            }
            if (id_flowlayout.selectedList.isEmpty()) {
                ToastUtils.toast(this, "请选择颜色")
                return@setOnClickListener
            }

            if (id_flowlayout1.selectedList.isEmpty()) {
                ToastUtils.toast(this, "请选择规格")
                return@setOnClickListener
            }

            val i = Integer.parseInt(numuber.text.toString())

            LogUtils.e("" + i, this)
            presenter.addShopCar(getProduct().id, i)
        }

        id_flowlayout.setOnTagClickListener { view, position, parent ->

            //如果另外一个没选择、那就要设置他是否可以选择
            //   if (id_flowlayout1.selectedList.isEmpty()) {

            //取消选择
            if (id_flowlayout.selectedList.isEmpty()) {
                setEanble(true, "can")
                return@setOnTagClickListener false
            }

            setEanble(true, clolor[position])
            refeshView()
            return@setOnTagClickListener false

        }

        id_flowlayout1.setOnTagClickListener { view, position, parent ->

            //取消选择
            if (id_flowlayout1.selectedList.isEmpty()) {
                setEanble(false, "can")
                return@setOnTagClickListener false
            }
            refeshView()
            setEanble(false, spec[position])
            return@setOnTagClickListener false
            //           }
//            isCheckProduct = true
//            true
        }
    }


    fun setEanble(isColer: Boolean, content: String) {
        val productlist = getProductList(isColer, content)
        if (isColer) {
            for (item in 0..spec.size - 1) {
                //取消选择，把其他的选择的非选择状态全部清空
                if (content.equals("can")) {
                    id_flowlayout1.getChildAt(item).isEnabled = true
                    continue
                }

                var haveProduct = false
                //这里是为了处理另外一个选择内容的状态，在这没办法确定我是否
                val text = spec[item]
                for (it in productlist) {
                    if (text.equals(it.spec)) {
                        haveProduct = true
                        break
                        //这里确定商品可用
                    }
                }
                id_flowlayout1.getChildAt(item).isEnabled = haveProduct
            }
        } else {
            for (item in 0..clolor.size - 1) {
                if (content.equals("can")) {
                    id_flowlayout.getChildAt(item).isEnabled = true
                    continue
                }
                var haveProduct = false
                var text = clolor[item]
                for (it in productlist) {  //循环可以使用的列表，
                    if (text.equals(it.color)) {//如果当前的在可使用列表，就直接跳出，显示可用
                        haveProduct = true
                        break
                    }
                }
                id_flowlayout.getChildAt(item).isEnabled = haveProduct

            }
        }
    }

    private fun refeshView() {
        run {
            if (id_flowlayout.selectedList.isEmpty() || id_flowlayout1.selectedList.isEmpty()) {
                return@run
            }
            mInitProduct = getProduct()
            price.text = (mInitProduct!!.price).Fromate()
            left.text = "库存: " + mInitProduct!!.stock
            if (mInitProduct!!.images!!.size != 0)
                Glide.with(App.app!!)
                    .load(mInitProduct?.images?.get(0))
                    .error(Glide.with(this).load(R.drawable.catery_child_default))
                    .into(image)
//            details1.text = getProduct().color
        }

    }

    fun getProductList(isColer: Boolean, content: String): ArrayList<productSpec> {

        var temList = arrayListOf<productSpec>()
        if (isColer) {
            for (it in productSpecList!!) {
                if (it.color.equals(content))
                    temList.add(it)
            }
        } else {
            for (it in productSpecList!!) {
                if (it.spec.equals(content))
                    temList.add(it)
            }
        }
        return temList
    }

    fun getProduct(): productSpec {

        if (id_flowlayout1.selectedList.size == 0 || id_flowlayout.selectedList.size == 0) {
            return mInitProduct!!
        }
        val temList = getProductList(true, clolor[id_flowlayout.selectedList.elementAt(0)])

        for (it in temList) {
            if (it.spec.equals(spec[id_flowlayout1.selectedList.elementAt(0)])) {
                it.appCount = Integer.parseInt(numuber.text.toString())
                LogUtils.e("-----------------", this)
                return it
            }
        }
        return mInitProduct!!
    }
}
