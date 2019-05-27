package com.ougong.shop.ActiivtyV2.brandtab.BrandActivity

import android.content.Context
import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baigui.commonlib.kotlinUtils.extensions.Fromate
import com.baigui.commonlib.utils.ScreenUtils
import com.bumptech.glide.Glide
import com.ougong.shop.ActiivtyV2.bean.BrandDetailBean
import com.ougong.shop.ActiivtyV2.bean.BrandDetailTagBean
import com.ougong.shop.App
import com.ougong.shop.Bean.ProductListItem
import com.ougong.shop.ConstString
import com.ougong.shop.R
import com.ougong.shop.activity.Product.ProductActivity
import kotlinx.android.synthetic.main.brand_detail_title.view.*
import kotlinx.android.synthetic.main.product_item.view.*
import kotlinx.android.synthetic.main.tab_view.view.*

class BrandAdapter(val context: Context, val stickyExampleModels: BrandDetailAdaptertBean) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var tabView : View? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(context).inflate(R.layout.brand_detail_title, parent, false)
                val params = view.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
                params.isFullSpan = true//最为重要的一个方法，占满全屏,以下同理
                view.setLayoutParams(params)

                return TitleViewHolder(view)
            }
            TYPE_TITLE -> {
//
//                val view = LayoutInflater.from(context).inflate(R.layout.test_text, parent, false)

               val view = tabView!!
                val params = view.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
                params.isFullSpan = true//最为重要的一个方法，占满全屏,以下同理
                view.setLayoutParams(params)
                return HeaderViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(context).inflate(R.layout.brand_product_item, parent, false)
                return HomeRecyclerViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (position == 0) {
            TYPE_HEADER
        } else if (position == 1) {
            TYPE_TITLE
        } else TYPE_NORMAL
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            is TitleViewHolder -> {
                Glide.with(App.app!!)
                    .load(stickyExampleModels.title!!.logo)
                    .into(viewHolder.view.brand_ice)

                Glide.with(App.app!!)
                    .load(stickyExampleModels.title!!.preview)
                    .into(viewHolder.view.brand_pic)

                viewHolder.view.product_count.text = "" + stickyExampleModels.title!!.productCount + "件"

                viewHolder.view.brand_describe.setText(stickyExampleModels.title!!.introduct)

                viewHolder.view.brand_describe.refesh()

            }
            is HeaderViewHolder -> {

//                viewHolder.view.tab.removeAllTabs()
//
//                viewHolder.view.tab.addTab(viewHolder.view.tab.newTab().setText("全部").setTag(0))
//
//                for (name in stickyExampleModels.head) {
//                    viewHolder.view.tab.addTab(viewHolder.view.tab.newTab().setText(name.name).setTag(name.id))
//                }
//
//                viewHolder.view.tab
//                viewHolder.view.tab.addOnTabSelectedListener(context as TabLayout.OnTabSelectedListener)
            }
            is HomeRecyclerViewHolder -> {
                Glide.with(App.app!!)
                    .load(stickyExampleModels.conten[position - 2].headImage)
                    .error(Glide.with(App.app!!).load(R.drawable.catery_child_default))
                    .into(viewHolder.view.producelist_item_image)

                if (position % 2 == 0){
                    viewHolder.view.setPadding(ScreenUtils.dipTopx(context,10f),0,0,0)
                }else{
                    viewHolder.view.setPadding(0,0,ScreenUtils.dipTopx(context,10f),0)

                }

                viewHolder.view.producelist_item_name.setText(stickyExampleModels.conten[position - 2].title)

                viewHolder.view.producelist_item_price.setText(stickyExampleModels.conten[position - 2].showPrice?.Fromate())

                viewHolder.view.setOnClickListener {
                    val intent = Intent(context, ProductActivity::class.java)
                    intent.putExtra(ConstString.PRODUCT_ITEM, stickyExampleModels.conten[position - 2])
                    context.startActivity(intent)
                }
            }


            else -> {
            }
        }
    }

    override fun getItemCount(): Int {

        if (stickyExampleModels.title == null) {
            return 0
        }
        if (stickyExampleModels.head.size == 0) {
            return 1
        }
        if (stickyExampleModels.conten == null) {
            return 2
        }
        return stickyExampleModels.conten!!.size + 2
    }

    inner class HomeRecyclerViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    inner class TitleViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    inner class HeaderViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    companion object {
        /**头视图 */
        val TYPE_HEADER = 0

        val TYPE_TITLE = 1
        /**正常的 */
        val TYPE_NORMAL = 2

    }
}

class BrandDetailAdaptertBean {
    var title: BrandDetailBean? = null
    var head: ArrayList<BrandDetailTagBean> = arrayListOf<BrandDetailTagBean>()
    var conten: ArrayList<ProductListItem> = arrayListOf<ProductListItem>()
}