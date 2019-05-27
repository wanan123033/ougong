package com.ougong.shop.activity.Order

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.baigui.commonlib.kotlinUtils.extensions.Fromate
import com.baigui.commonlib.utils.LogUtils
import com.bumptech.glide.Glide
import com.ougong.shop.App
import com.ougong.shop.Bean.OrderBean
import com.ougong.shop.Bean.Product
import com.ougong.shop.Bean.SpecificationCartVos
import com.ougong.shop.ConstString
import com.ougong.shop.R
import com.ougong.shop.activity.MainShopCarFragment.ChooseAddress.ChooseAddress
import com.ougong.shop.activity.Maininfo.mangeAdress.AddressBean
import com.ougong.shop.activity.Maininfo.mangeAdress.addAddress.AddAddress
import com.ougong.shop.fragment.NoAddressDialog
import kotlinx.android.synthetic.main.fragment_homepage_v2.*
import kotlinx.android.synthetic.main.order_address.view.*
import kotlinx.android.synthetic.main.order_detail_foot.view.*
import kotlinx.android.synthetic.main.order_note.view.*
import kotlinx.android.synthetic.main.order_pay_way_title.view.*
import kotlinx.android.synthetic.main.order_post_way_item.view.*
import kotlinx.android.synthetic.main.order_post_way_item1.view.*
import kotlinx.android.synthetic.main.order_price_item.view.*
import kotlinx.android.synthetic.main.order_product_item.view.*
import java.text.BreakIterator
import java.text.DecimalFormat

class OrderAdapter(private val mContext: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mbean: OrderBean? = null

    var addressBean: AddressBean? = null
    private var inflater: LayoutInflater?

    init {
        inflater = LayoutInflater.from(mContext)
    }

    var mProductList: List<SpecificationCartVos>? = null
    fun setOrderBean(orderBean: OrderBean) {
        mbean = orderBean
        mProductList = getProductList()
        notifyDataSetChanged()
    }

    private fun getProductList(): List<SpecificationCartVos>? {
        val value: ArrayList<SpecificationCartVos> = arrayListOf()
        if (mbean != null) {
            for (it in mbean!!.products!!) {
                value.addAll(it.specificationCartVos!!)
            }
        } else {
            return null
        }

        return value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ADDRESS_ITEM -> {
                val viewtop = inflater!!.inflate(R.layout.order_address, parent, false)
                AddressHolder(viewtop)
            }

            PRODUCT_ITEM -> {
                val viewtop = inflater!!.inflate(R.layout.order_product_item, parent, false)
                ProductHolder(viewtop)
            }

            PRICE_ITEM -> {
                val viewtop = inflater!!.inflate(R.layout.order_price_item, parent, false)
                PriceHolder(viewtop)

            }

            PAY_WAY_TITLE -> {
                val viewtop = inflater!!.inflate(R.layout.order_pay_way_title, parent, false)
                ComonHolder(viewtop)

            }

            PAY_WAY_ITEM -> {
                val viewtop = inflater!!.inflate(R.layout.order_pay_way_item, parent, false)
                PayWayHolder(viewtop)
            }

            POST_WAY_TITLE -> {
                val viewtop = inflater!!.inflate(R.layout.order_pay_way_title, parent, false)
                viewtop.title_title.text = "请选择配送方式"
                ComonHolder(viewtop)

            }

            POST_WAY_TIEM -> {
                val viewtop = inflater!!.inflate(R.layout.order_post_way_item, parent, false)
                PostWayHolder(viewtop)
            }

            POST_WAY_TIEM1 -> {
                val viewtop = inflater!!.inflate(R.layout.order_post_way_item1, parent, false)
                PostWayHolder1(viewtop)
            }
            NOTE -> {
                val viewtop = inflater!!.inflate(R.layout.order_note, parent, false)
                NoteHolder(viewtop)
            }
            else -> {
                val viewtop = inflater!!.inflate(R.layout.include_shopcar_brand, parent, false)
                ProductHolder(viewtop)
            }
        }
    }


    override fun getItemCount(): Int {

        return (mProductList?.size ?: 0) + 8
//        if (mbean != null){
//            var count = 7
//            mbean!!.products?.forEach {
//                count = count+it.specificationCartVos!!.size
//
//            }
//            return count
//        }
//        else
//            return 7
//        return (mbean?.products?.size ?: 0) + 7
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {

        when (holder) {
            is AddressHolder -> {
                initAddress(holder)
            }

            is ProductHolder -> {
                Glide.with(App.app!!)
                    .load(mProductList!![p1 - 1].product!!.headImage)
                    .error(Glide.with(mContext!!).load(R.drawable.catery_child_default))
                    .into(holder.view.ice)
                holder.view.title.text = mProductList!![p1 - 1].title
                holder.view.details1.text = mProductList!![p1 - 1].spec + "  " + mProductList!![p1 - 1].color
//                holder.view.details2.text = mProductList!![p1 - 1].color
                holder.view.price.text = mProductList!![p1 - 1].price.Fromate()

                holder.view.number.text = "×" + mProductList!![p1 - 1].count.toString()
            }
            is PriceHolder -> {

                initPrice(holder)

            }

            is PostWayHolder -> {
                holder.view.post_isDefault.isChecked = getIsDefault(1)

                holder.view.post_title.text = "自选物流"
                holder.view.setOnClickListener {
                    if (!holder.view.post_isDefault.isChecked) {
                        mDefaultPost = 2
                        notifyDataSetChanged()
                    }
                }
            }

            is PostWayHolder1 -> {
                holder.view.post_isDefault1.isChecked = getIsDefault(2)

                holder.view.post_title1.text = "欧工物流"
                    if (holder.view.post_isDefault1.isChecked){
                        holder.view.introduce_post_way.visibility = View.VISIBLE
                    }else{
                        holder.view.introduce_post_way.visibility = View.GONE
                    }
                holder.view.setOnClickListener {
                    if (!holder.view.post_isDefault1.isChecked) {
                        mDefaultPost = 1
                        notifyDataSetChanged()
                    }else{

                    }
                }
            }
            is NoteHolder -> {
                holder.view.note_content.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        LogUtils.e(s.toString(), this)
                        noteString = s.toString()
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }
                })
            }
        }
    }

    var noteString: String? = null
    var mDefaultPost = 1
    private fun getIsDefault(i: Int): Boolean {
        return i == mDefaultPost
    }

    private fun initPrice(holder: PriceHolder) {
        var productPrice = 0.toDouble()
        var PostPrice = 0.toDouble()
        var WoodPrice = 0.toDouble()
        var ServicetPrice = 0.toDouble()
        if (mProductList == null)
            return
//        for (ite in mProductList!!) {
//            price = ite.price + price
//        }

        if (mbean != null) {
            for (it in mbean!!.products!!) {
                var temCube = 0f
                for (item in it.specificationCartVos!!){
//                    temWood = temWood + item.
                    temCube = temCube + item.cube * item.count
                    productPrice = productPrice + item.price * item.count
                }
                WoodPrice = WoodPrice + temCube * it.woodenPriceFactor
                Log.e("TAG====",""+temCube+"----"+it!!.shortFreight)
                if (temCube > it!!.shortFreight!!.cubeThreshold) {
                    PostPrice = PostPrice + (temCube-it!!.shortFreight!!.cubeThreshold) * it!!.shortFreight!!.unitPrice + it!!.shortFreight!!.startPrice
                }else{
                    PostPrice = PostPrice + it!!.shortFreight!!.startPrice
                }
            }
        }

        ServicetPrice = (mbean!!.serviceFeeFactor * productPrice)

        holder.view.order_serve.text = ServicetPrice.Fromate()

        holder.view.order_total.text = productPrice.Fromate()


        if (mContext is OrderActivity)
            mContext.setPrice("应付金额: "+(ServicetPrice + WoodPrice + PostPrice + productPrice).Fromate())
        else if (mContext is QuickOrderActivity){
            mContext.setPrice("应付金额: " + (ServicetPrice + WoodPrice + PostPrice + productPrice).Fromate())
        }
        holder.view.order_transport.text = WoodPrice.Fromate()

        holder.view.order_wood.text = PostPrice.Fromate()
//        holder.view.order_total.text = .toString()
    }

    private fun initAddress(holder: AddressHolder) {

        var address: AddressBean? = null
        /**
         * 在activity中，已经处理了，所以else是没意义的
         */
        if (addressBean != null) {
            address = addressBean
        } else {
            mbean?.addressList?.let {
                for (it in mbean!!.addressList!!) {
                    if (it.isDefault == true) {
                        address = it
                        break
                    }
                }
            }
        }
        holder.view.is_default.visibility = View.GONE
        if (null == address) {
            holder.view.address_name.visibility = View.GONE
            holder.view.address_phone.visibility = View.GONE
            holder.view.address.visibility = View.GONE
            holder.view.order_usless1.visibility = View.GONE
            holder.view.add_address.visibility = View.VISIBLE
            holder.view.add_address_text.visibility = View.VISIBLE
            holder.view.adress_container.setOnClickListener {
                var intent = Intent(mContext, ChooseAddress::class.java)
                intent.putExtra(ConstString.CHOOSE_ADDRESS_HANDLE, -1)
                (mContext!! as Activity).startActivityForResult(intent, ConstString.CHOOSE_ADDRESS_REQUEST)
            }
        } else {
            holder.view.address_name.visibility = View.VISIBLE
            holder.view.address_phone.visibility = View.VISIBLE
            holder.view.address.visibility = View.VISIBLE
            holder.view.order_usless1.visibility = View.VISIBLE
            holder.view.add_address.visibility = View.GONE
            holder.view.add_address_text.visibility = View.GONE

            holder.view.address_name.text = "收货人:  " + address!!.name

            if (address!!.isDefault){
                holder.view.is_default.visibility = View.VISIBLE
            }
            holder.view.address_phone.text = address!!.mobile

            holder.view.address.text = address!!.addressInfo

            holder.view.adress_container.setOnClickListener {

                var intent = Intent(mContext, ChooseAddress::class.java)
                intent.putExtra(ConstString.CHOOSE_ADDRESS_HANDLE,address!!.id)
                (mContext!! as Activity).startActivityForResult(intent, ConstString.CHOOSE_ADDRESS_REQUEST)
            }
        }
//
//        holder.view.setOnClickListener {
//
//            var intent = Intent(mContext, ChooseAddress::class.java)
//            intent.putExtra()
//
//            (mContext!! as Activity).startActivityForResult(
//                Intent(mContext, ChooseAddress::class.java),
//                ConstString.CHOOSE_ADDRESS_REQUEST
//            )
//        }

    }

    private val ADDRESS = 1

    private val THE_LAST = 0

    private val ADDRESS_ITEM = 2

    private val PRODUCT_ITEM = 3

    private val PRICE_ITEM = 4


    private val PAY_WAY_TITLE = 5

    private val PAY_WAY_ITEM = 6

    private val POST_WAY_TITLE = 7

    private val POST_WAY_TITLE1 = 11

    private val POST_WAY_TIEM = 8

    private val POST_WAY_TIEM1 = 9

    private val NOTE = 10
    //
//    protected val
    override fun getItemViewType(position: Int): Int {
        val count = mProductList?.size ?: 0
        return when (position) {
            0 -> ADDRESS_ITEM
            in 1 until 1 + count -> PRODUCT_ITEM
            1 + count -> PRICE_ITEM
            2 + count -> PAY_WAY_TITLE
            3 + count -> PAY_WAY_ITEM
            4 + count -> POST_WAY_TITLE
            5 + count -> POST_WAY_TIEM
            6 + count -> POST_WAY_TIEM1
            7 + count -> NOTE
            else -> THE_LAST
        }
    }

    inner class AddressHolder(var view: View) : RecyclerView.ViewHolder(view)
    inner class ProductHolder(var view: View) : RecyclerView.ViewHolder(view)
    inner class PriceHolder(var view: View) : RecyclerView.ViewHolder(view)
    inner class PayWayHolder(var view: View) : RecyclerView.ViewHolder(view)
    inner class PostWayHolder(var view: View) : RecyclerView.ViewHolder(view)

    inner class PostWayHolder1(var view: View) : RecyclerView.ViewHolder(view)

    inner class NoteHolder(var view: View) : RecyclerView.ViewHolder(view)

    inner class ComonHolder(view: View) : RecyclerView.ViewHolder(view)
}

