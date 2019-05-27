package com.ougong.shop.adapter

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baigui.commonlib.utils.LogUtils
import com.bumptech.glide.Glide
import com.ougong.shop.App
import com.ougong.shop.Bean.*
import com.ougong.shop.R
import com.ougong.shop.utils.GlideImageLoader
import com.ougong.shop.utils.RImageLoader
import kotlinx.android.synthetic.main.homepage_banner_v2.view.*
import kotlinx.android.synthetic.main.homepage_content_v2.view.*
import kotlin.collections.ArrayList

class HomePageAdapter(private val mContext: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mList = SparseArray<fuckProcudtList<ProductListItem>>(3)
    private var mBean: fuckNetbean<CateryBean>? = null
    private val inflater: LayoutInflater

    var mlistener: onBannerchange? = null

    init {
        inflater = LayoutInflater.from(mContext)
        //初始化各我数据源

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            0 -> {
                val viewtop = inflater.inflate(R.layout.homepage_banner_v2, parent, false)
                val params = viewtop.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
                params.isFullSpan = true//最为重要的一个方法，占满全屏,以下同理
                params.topMargin = 40
                viewtop.setLayoutParams(params)
                TypeBanner(viewtop)
            }
            else -> {
                val view2 = inflater.inflate(R.layout.homepage_content_v2, parent, false)

                val params = view2.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
                params.isFullSpan = true
                view2.setLayoutParams(params)
                TypeContent(view2)
            }
        }
    }

    override fun getItemCount() = 3

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TypeBanner -> {
                val urls = mContext!!.getResources().getStringArray(R.array.url)
                val list = ArrayList<Int>(3)
                list.add(R.drawable.picture_err)
                list.add(R.drawable.homepage_recommend)
                list.add(R.drawable.picture_err)
                holder.view.banner.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(p0: Int) {

                    }

                    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                    }

                    override fun onPageSelected(p0: Int) {
//                        mlistener?.onchange(position,p0)
                    }

                })
                holder.view.banner.setImages(list)
                    .setImageLoader(RImageLoader())
                    .start()
            }
            is TypeContent -> {
                val urls = mContext!!.getResources().getStringArray(R.array.url)
             //   bannerHelper.help(holder.view.mz_banner, urls)
                holder.view.mz_banner.addPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(p0: Int) {

                    }

                    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                    }

                    override fun onPageSelected(p0: Int) {
                        mlistener?.onchange(position, p0)
                    }

                })

                if (mList.get(position) != null) {
                    if (mList.get(position).total == 0)
                        return
                    holder.view.item1_name.setText(mList.get(position).rows[0].headImage)
                    Glide.with(App.app!!)
                        .load(mList.get(position).rows[0].title)
                        .error(Glide.with(mContext).load(R.drawable.catery_child_default))
                        .into(holder.view.item1_image)

//                    holder.view.item2_name.setText(list.get(position).data[1].title)

                    if (mList.get(position).total == 1)
                        return

                    holder.view.item2_name.setText(mList.get(position).rows[1].headImage)

                    Glide.with(App.app!!)
                        .load(mList.get(position).rows[1].title)
                        .error(Glide.with(mContext).load(R.drawable.catery_child_default))
                        .into(holder.view.item2_image)

                    if (mList.get(position).total == 2)
                        return

                    holder.view.item3_name.setText(mList.get(position).rows[2].title)

                    Glide.with(App.app!!)
                        .load(mList.get(position).rows[2].headImage)
                        .error(Glide.with(mContext).load(R.drawable.catery_child_default))
                        .into(holder.view.item3_image)

                }
            }
            else -> {
//                val urls = mContext!!.getResources().getStringArray(R.array.url)
//                bannerHelper.help(holder.view.banner,urls)
            }
        }
    }


    fun addBannerchangeListener(onBannerchange: onBannerchange) {

        mlistener = onBannerchange
    }

    interface onBannerchange {
        fun onchange(postion: Int, bannerPostion: Int)
    }

    fun setData(bean: fuckNetbean<CateryBean>) {

        mBean = bean
        notifyDataSetChanged()
    }

    fun setShowData(bean: fuckProcudtList<ProductListItem>, index: Int) {

        LogUtils.e(">>>>>>>>setShowData",this)
        mList.put(index, bean)
        notifyDataSetChanged()
    }

    inner class TypeBanner(val view: View) : RecyclerView.ViewHolder(view)

    inner class TypeContent(val view: View) : RecyclerView.ViewHolder(view)
}