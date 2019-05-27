package com.ougong.shop.activity.Product.ProductDetail

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.ougong.shop.Bean.fuckProcudtList
import com.ougong.shop.Bean.productDetailKeyValue
import com.ougong.shop.R
import com.ougong.shop.base_mvp.base.BaseActivity

import kotlinx.android.synthetic.main.content_product_detail.*
import kotlinx.android.synthetic.main.layout_student_item.view.*

class ProductDetailActivity : BaseActivity<ProductDetailContract.View, ProductDetailContract.Presenter>(),
    ProductDetailContract.View {
    override fun getDetailSucess(bean: fuckProcudtList<productDetailKeyValue>) {

        mAdapter?.mBean = bean.rows
        mAdapter?.notifyDataSetChanged()
    }

    var mBean: Array<productDetailKeyValue> = arrayOf()

    override fun setContentViewSource() = R.layout.content_product_detail

    override fun initPresenter() = ProductDetailPresenter()

    var mAdapter : ProductAdapter? = null
    override fun initView() {
        StarusBarUtils.transparencyBar(this)
        StarusBarUtils.setAndroidNativeLightStatusBar(this, true)


        var id = intent?.getIntExtra("id",0)?:0
        if (id == 0){
            return
        }

        back.setOnClickListener { finish() }
        presenter.getDetails(id)
        mAdapter =  ProductAdapter(this,mBean)
        detailList.adapter = mAdapter as ProductAdapter
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        finish()
        return true
    }

}

class ProductAdapter(context: Context,var mBean: Array<productDetailKeyValue>) : ArrayAdapter<productDetailKeyValue>(context,0,mBean) {

    var inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        var holder: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.layout_student_item, null)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = convertView!!.getTag() as ViewHolder
            view = convertView
        }
        holder.view.content.text = mBean?.get(position)?.value
        holder.view.title.text = mBean?.get(position)?.name
        //在view视图中查找id为image_photo的控件
        return view
    }

//    override fun getItem(position: Int) = mBean?.get(position)

//    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = mBean?.size ?: 0

    inner class ViewHolder(var view: View) {
    }

}