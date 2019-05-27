package com.ougong.shop.V1

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ougong.shop.Bean.HomeCategory
import com.ougong.shop.R
import kotlinx.android.synthetic.main.homepage_item_title.view.*
import kotlinx.android.synthetic.main.homepage_recycleview.view.*
import com.zhouwei.mzbanner.holder.MZViewHolder
import kotlin.collections.ArrayList


/**
 * Created by yyd on 2017/2/2.
 */
class HomepagerRecycleAdapter(private val mContext: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mHotProductList: List<ProdectBean>? = null
    private var count = 300
    private val mMainProductList: MutableList<ProdectBean>?
    private var mRecommendProductList: List<ProdectBean>? = null
    private var mHomeCategories: ArrayList<HomeCategory>? = null
    private val TYPE_INPUT = 1//头部输入框
    private val TYPE_BANNER = 2//轮播图
    private val TYPE_CATEGORIES = 3//四个分类列表
    private val TYPE_HEAD = 4//四个分类列表
    private val TYPE_HORSON_RECYCLEVIEW = 5//四个分类列表
    private val TYPE_3_IMAGE = 6//四个分类列表
    private val TYPE_BANNER_2 = 7//四个分类列表
    private val TYPE_DEFAULT = 8//四个分类列表
    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
        //初始化各我数据源
        mHotProductList = ArrayList<ProdectBean>()
        mMainProductList = ArrayList<ProdectBean>()
        mRecommendProductList = ArrayList<ProdectBean>()


    }

    override fun getItemViewType(position: Int): Int {

        var position1 = position
//        if (position > 1){
//            position1 = position + 9
//        }

//        return TYPE_DEFAULT
        return when(position1){
            0-> TYPE_INPUT
            1->TYPE_BANNER
            2-> TYPE_CATEGORIES
            3,5,7,9,11-> TYPE_HEAD
            4, 6 -> TYPE_HORSON_RECYCLEVIEW
            8-> TYPE_3_IMAGE
            10-> TYPE_BANNER_2
            else-> TYPE_DEFAULT
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType) {
            TYPE_INPUT -> {
                val viewtop = inflater.inflate(R.layout.include_input_item, parent, false)
                val params = viewtop.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
                params.isFullSpan = true//最为重要的一个方法，占满全屏,以下同理
                params.topMargin = 40
                viewtop.setLayoutParams(params)
                TypeInputHolder(viewtop)
            }
            TYPE_BANNER -> {
                val view2 = inflater.inflate(R.layout.homepage_banner, parent, false)

                val params = view2.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
                params.isFullSpan = true
                view2.setLayoutParams(params)
                TypeBanner_1_Holder(view2)
            }
            TYPE_CATEGORIES -> {
                //中间head下面的布局
                val view = inflater.inflate(R.layout.homepage_recycleview, parent, false)
                val params2 = view.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
                params2.isFullSpan = true
                view.setLayoutParams(params2)
                TypeCategoriesHolder(view)
            }
            TYPE_HEAD ->{
                val view = inflater.inflate(R.layout.homepage_item_title, parent, false)
                val params2 = view.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
                params2.isFullSpan = true
                view.setLayoutParams(params2)
                TypeItemTitleHolder(view)
            }
            TYPE_HORSON_RECYCLEVIEW -> {
                val view = inflater.inflate(R.layout.homepage_recycleview, parent, false)
                val params2 = view.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
                params2.isFullSpan = true
                view.setLayoutParams(params2)
                TypeHorsinHolder(view)
            }
            TYPE_DEFAULT->{
                return Type_ContentHolder(inflater.inflate(R.layout.homepage_content_item, parent, false))
            }
            else -> {
                return Type_ContentHolder(inflater.inflate(R.layout.homepage_content_item, parent, false))
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is TypeInputHolder -> initInput(holder)//加载四个category数据源
            is TypeBanner_1_Holder -> initBanner(holder)
            is TypeCategoriesHolder -> initeCategorie(holder)
            is TypeItemTitleHolder -> initTitle(holder,position)
            is TypeHorsinHolder -> initHors(holder,position)
            is Type_ContentHolder -> initContent(holder)
            else -> {}
        }


    }

    private fun initBanner(holder: TypeBanner_1_Holder) {

        val urls = mContext!!.getResources().getStringArray(R.array.url)

//        holder.view.banner.setImages(ArrayList(list))
//            .setImageLoader(GlideImageLoader())
//            .start()
//
//
//        holder.view.banner.setPageTransformer(true, CoverModeTransformer(holder.view.banner.viewPager))
//        bannerHelper.help(holder.view.banner,urls)
//        holder.view.banner.setPages(movies, new )

//        ZoomOutPageTransformer
//        holder.view.banner.startAutoPlay()
    }



    private fun initContent(holder: Type_ContentHolder) {

    }

    class BannerViewHolder : MZViewHolder<String> {
        private var mImageView: ImageView? = null
        override fun createView(context: Context): View {
            val view = ImageView(context)
            mImageView = view
            return view
        }

        override fun onBind(context: Context, i: Int, movie: String) {
//            Picasso.with(context).load(movie).into(mImageView)
//            mImageView?.let {
//                Glide.with(context)
//                    .load(movie)
//                    .into(it)
//            }

            mImageView?.setImageResource(movie.toInt())
        }
    }
    private fun initeCategorie(holder: TypeCategoriesHolder) {
        holder.view.homepage_recyclerView_horizontal.setLayoutManager(GridLayoutManager(mContext, 4))

        val categoryAdapter = HomePageCategoriesAdapter(mContext!!)

        holder.view.homepage_recyclerView_horizontal.setAdapter(categoryAdapter)
    }

    private fun initTitle(holder: TypeItemTitleHolder, position: Int) {
        holder.view.Title_text.text = "猜你喜欢"
    }

    private fun initInput(holder: TypeInputHolder) {
    }

    private fun initHors(holder: TypeHorsinHolder, i: Int) {
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        holder.view.homepage_recyclerView_horizontal!!.layoutManager = linearLayoutManager
        val adapter = HomePageHorizontal(mContext!!)
        holder.view.homepage_recyclerView_horizontal!!.adapter = adapter

    }



    override fun getItemCount(): Int {
        return count
    }

    fun setCategoryBean(homeCategories: ArrayList<HomeCategory>) {
        mHomeCategories = homeCategories
        count++
        notifyDataSetChanged()

    }

    //头部Viewpager viewholder
    inner class TypeInputHolder(view: View) : RecyclerView.ViewHolder(view)

    //头部Viewpager viewholder
    inner class Type_DdfaultHolder(view: View) : RecyclerView.ViewHolder(view)

    //头部Viewpager viewholder
    inner class Type_ContentHolder(var view: View) : RecyclerView.ViewHolder(view)
    /**
     * 水平滑动
     */
    inner class TypeHorsinHolder(var view: View) : RecyclerView.ViewHolder(view)

    inner class TypeBanner_1_Holder(var view: View) : RecyclerView.ViewHolder(view)

    /**
     * 中间四个分类按钮
     */
    inner class TypeCategoriesHolder(val view: View) : RecyclerView.ViewHolder(view)


    inner class  TypeItemTitleHolder(val view: View) : RecyclerView.ViewHolder(view)

//    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
//        super.onAttachedToRecyclerView(recyclerView)
//
//        this.recyclerView = recyclerView
//
//    }
}
