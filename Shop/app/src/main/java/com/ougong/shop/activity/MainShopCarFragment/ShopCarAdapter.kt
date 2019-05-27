package com.ougong.shop.activity.MainShopCarFragment

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import com.baigui.commonlib.kotlinUtils.extensions.Fromate
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ToastUtils
import com.bumptech.glide.Glide
import com.ougong.shop.Bean.BrandList
import com.ougong.shop.Bean.BrandList.Companion.APP_TOCHECK_ED
import com.ougong.shop.Bean.BrandList.Companion.APP_TO_CHECK
import com.ougong.shop.Bean.BrandList.Companion.CHECK
import com.ougong.shop.Bean.BrandList.Companion.CHEKED
import com.ougong.shop.Bean.FuckData
import com.ougong.shop.Bean.ProductListItem
import com.ougong.shop.Bean.SpecificationCartVos
import com.ougong.shop.ConstString
import com.ougong.shop.R
import com.ougong.shop.activity.Product.ProductActivity
import com.ougong.shop.activity.ProductList.midle.ProductMidleListActivity
import kotlinx.android.synthetic.main.fragment_homepage_v2.*
import kotlinx.android.synthetic.main.include_shopcar_bottom_item.view.*
import kotlinx.android.synthetic.main.include_shopcar_brand.view.*
import kotlinx.android.synthetic.main.include_shopcar_item.view.*

class ShopCarAdapter(private val mContext: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var inflater: LayoutInflater?

    init {
        inflater = LayoutInflater.from(mContext)
    }

    var mBean: FuckData? = null

    /** 数据是需要翻译的，但是这里做估计会比较麻烦，没办法整理好一个好的数据结构，这里就暂时妈的直接用，以后再思考
     *
     */
    fun setData(fuckData: FuckData) {
        mBean = fuckData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            BRAND_TITLE -> {
                val viewtop = inflater!!.inflate(R.layout.include_shopcar_brand, parent, false)
                brandTitleHolder(viewtop)
            }

            SHOPCAR_ITEM -> {
                val viewtop = inflater!!.inflate(R.layout.include_shopcar_item, parent, false)
                ShopItemHolder(viewtop)
            }

            SHOPCAR_BOTTMO_ITEM -> {
                val viewtop = inflater!!.inflate(R.layout.include_shopcar_bottom_item, parent, false)
                ShopItemBottomHolder(viewtop)
            }

            THE_LAST -> {

                val viewtop = inflater!!.inflate(R.layout.include_shopcar_bottom, parent, false)
                BottomHolder(viewtop)

            }

            else -> {
                val viewtop = inflater!!.inflate(R.layout.include_shopcar_brand, parent, false)
                brandTitleHolder(viewtop)
            }

        }
    }

    override fun getItemCount(): Int {
        if (null == mBean)
            return 0
        if ( mBean!!.brandList == null)
            return 0
        var count = 0
        for (it in mBean!!.brandList!!) {
            count = count + it.specificationCartVos!!.size + 1
        }
        return count
    }

    fun refeshDataFromeParent() {

        if (mBean == null)
            return
        var isAllCheck = true
        for (it in mBean!!.brandList!!) {
            if (it.appCheckState == APP_TOCHECK_ED) {

                for (item in it.specificationCartVos!!) {
                    item.appCheckState = CHEKED
                }
                it.appCheckState = CHEKED
                continue
            }
            if (it.appCheckState == APP_TO_CHECK) {
                for (item in it.specificationCartVos!!) {
                    item.appCheckState = CHECK
                }
                it.appCheckState = CHECK
                isAllCheck = false
                continue
            }
            isAllCheck = it.appCheckState == CHEKED && isAllCheck
        }

        mBean?.isAllSelect = isAllCheck

    }

    fun refeshFromeChild() {

        for (it in mBean!!.brandList!!) {

            //这里为了逻辑简单点，解决办法是先判断是不是从外部发生装状态变化，性能就不考虑了，
            for (item in it.specificationCartVos!!) {
                if (item.appCheckState == APP_TOCHECK_ED) {

                    for (item1 in it.specificationCartVos!!) {
                        if (item1.appCheckState == CHECK) {
                            item.appCheckState = CHEKED
                            break
                        }
                    }
                    item.appCheckState = CHEKED
                    it.appCheckState = CHEKED
                    break
                }

                if (item.appCheckState == APP_TO_CHECK) {

                    it.appCheckState = CHECK
                    item.appCheckState = CHECK
                    break

                }
            }
        }
        //不想纠结直接从父类里面写吧，，，
        refeshDataFromeParent()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is brandTitleHolder -> {
                val item = getBrand(position)
                holder.view.brand_name.text = item.name

                holder.view.checkbox.isChecked = item.appCheckState == CHEKED
                holder.view.checkbox.setOnClickListener {
                    if ((it as Checkable).isChecked) {
                        LogUtils.e("ischeck", this)
                        item.appCheckState = APP_TOCHECK_ED
                    } else {
                        LogUtils.e("ischeck false", this)
                        item.appCheckState = APP_TO_CHECK
                    }
                    refeshDataFromeParent()
                    notifyDataSetChanged()

                    mOnSelectChange?.onchange()
                }

            }
            is ShopItemHolder -> {
                val item = getContent(position)
                holder.view.title.text = item.title
                holder.view.price.text = (item.price).Fromate()
                holder.view.number.text = item.count.toString()
                if (item.images != null && item.images!!.size != 0) {
                    Glide.with(mContext!!)
                        .load(item.images!![0])
                        .error(Glide.with(mContext).load(R.drawable.catery_child_default))
                        .into(holder.view.ice)
                }else {
                    item.product?.headImage?.let {
                        Glide.with(mContext!!)
                            .load(it)
                            .error(Glide.with(mContext).load(R.drawable.catery_child_default))
                            .into(holder.view.ice)
                    }
                }

                holder.view.ice.setOnClickListener {
                    val intent = Intent(mContext, ProductActivity::class.java)
                    val tem = ProductListItem().apply {
                        //todo  没必要填满
                        id = item.productId
                    }
                    intent.putExtra(ConstString.PRODUCT_ITEM,tem)
                    mContext?.startActivity(intent)
                }
                holder.view.details1.text = item.spec + "  " + item.color
//                holder.view.details2.text = item.color
                holder.view.check_box.isChecked = item.appCheckState == CHEKED
                holder.view.check_box.setOnClickListener {
                    if ((it as Checkable).isChecked) {
                        item.appCheckState = APP_TOCHECK_ED
                    } else {
                        item.appCheckState = APP_TO_CHECK
                    }
                    refeshFromeChild()
                    notifyDataSetChanged()
                    mOnSelectChange?.onchange()
                }

                holder.view.pluse.setOnClickListener {
                    if (item.count == 99) {
                        ToastUtils.toast(mContext,"数量不多于99")
                        return@setOnClickListener
                    }

                    if (item.count >= item.stock)
                        return@setOnClickListener
                    holder.view.number.setText((++item.count).toString())
                    mOnSelectChange?.onChangeNumber(item,true)
                }

                holder.view.minus.setOnClickListener {
                    if (item.count == 1) {
                        ToastUtils.toast(mContext,"数量不能少于1")
                        return@setOnClickListener
                    }
                    holder.view.number.text = (--item.count).toString()
                    mOnSelectChange?.onChangeNumber(item,false)
                }
            }
            is ShopItemBottomHolder -> {
                val item = getContent(position)
                holder.view.title_bottom.text = item.title
                holder.view.price_bottom.text = (item.price).Fromate()
                holder.view.number_bottom.text = item.count.toString()

                if (item.images != null && item.images!!.size != 0)
                    Glide.with(mContext!!)
                        .load(item.images!![0])
                        .error(Glide.with(mContext).load(R.drawable.catery_child_default))
                        .into(holder.view.ice_bottom)

                holder.view.ice_bottom.setOnClickListener {
                    val intent = Intent(mContext, ProductActivity::class.java)
                    val tem = ProductListItem().apply {
                        //todo  没必要填满
                        id = item.productId
                    }
                    intent.putExtra(ConstString.PRODUCT_ITEM,tem)
                    mContext?.startActivity(intent)
                }

                holder.view.details1_bottom.text = item.spec + "  " + item.color
//                holder.view.details2_bottom.text = item.color
                holder.view.check_box_bottom.isChecked = item.appCheckState == CHEKED
                holder.view.check_box_bottom.setOnClickListener {

                    if ((it as Checkable).isChecked) {
                        item.appCheckState = APP_TOCHECK_ED

                    } else {
                        item.appCheckState = APP_TO_CHECK
                    }
                    refeshFromeChild()
                    notifyDataSetChanged()
                    mOnSelectChange?.onchange()
                }


                holder.view.pluse_bottom.setOnClickListener {

                    if (item.count == 99) {
                        ToastUtils.toast(mContext,"数量不多于99")
                        return@setOnClickListener
                    }

                    if (item.count >= item.stock)
                        return@setOnClickListener

                    holder.view.number_bottom.setText((++item.count).toString())
                    mOnSelectChange?.onChangeNumber(item,true)
                }

                holder.view.minus_bottom.setOnClickListener {
                    if (item.count == 1) {
                        ToastUtils.toast(mContext,"数量不能少于1")
                        return@setOnClickListener
                    }

                    holder.view.number_bottom.setText((--item.count).toString())
                    mOnSelectChange?.onChangeNumber(item,false)
                }
            }

        }
    }


    private fun getContent(position: Int): SpecificationCartVos {
        var count = 0
        for (it in mBean!!.brandList!!) {


            count = count + it.specificationCartVos!!.size + 1

            if (count > position)
                return it.specificationCartVos!![count - position - 1]
        }
        return mBean!!.brandList!![0].specificationCartVos!![0]

    }

    /**
     * 理论上这个是不会错的。
     */
    private fun getBrand(position: Int): BrandList {
        var count = 0
        for (it in mBean!!.brandList!!) {

            if (count == position)
                return it
            count = count + it.specificationCartVos!!.size + 1
        }
        return mBean!!.brandList!![0]
    }

    private val BRAND_TITLE = 1

    private val THE_LAST = 0

    private val SHOPCAR_ITEM = 2

    private val SHOPCAR_BOTTMO_ITEM = 3

    //这里商品类分为两类，普通中间项。底部项，和最底部。其实这里最底部可以放一段没用的汉字提示下没内容了
    override fun getItemViewType(position: Int): Int {

        var count = 0
        for (it in mBean!!.brandList!!) {
            if (position == count) {
                return BRAND_TITLE
            }
            count = count + it.specificationCartVos!!.size + 1

            if (position < count) {
                if (position == count - 1)
                    return SHOPCAR_BOTTMO_ITEM
                return SHOPCAR_ITEM
            }
        }
        return THE_LAST
    }

    var mOnSelectChange: OnSelectChange? = null

    fun setOnSelect(onSelectChange: OnSelectChange) {
        mOnSelectChange = onSelectChange
    }

    inner class brandTitleHolder(var view: View) : RecyclerView.ViewHolder(view)
    inner class ShopItemHolder(var view: View) : RecyclerView.ViewHolder(view)

    inner class ShopItemBottomHolder(var view: View) : RecyclerView.ViewHolder(view)

    inner class BottomHolder(var view: View) : RecyclerView.ViewHolder(view)
//    inner class RecomandTitleHolder(view: View) : RecyclerView.ViewHolder(view)
//    inner class RecomandItemHolder(view: View) : RecyclerView.ViewHolder(view)
}

interface OnSelectChange {
    fun onchange()
    fun onChangeNumber(Postion: SpecificationCartVos, isPluse: Boolean)
}