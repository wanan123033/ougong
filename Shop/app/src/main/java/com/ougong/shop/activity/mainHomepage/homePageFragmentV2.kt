package com.ougong.shop.activity.mainHomepage

import android.content.Intent
import android.support.v4.view.ViewPager
import android.view.View
import com.baigui.commonlib.kotlinUtils.extensions.Fromate
import com.baigui.commonlib.utils.LogUtils
import com.bumptech.glide.Glide
import com.ougong.shop.App
import com.ougong.shop.Bean.*
import com.ougong.shop.ConstString
import com.ougong.shop.R
import com.ougong.shop.utils.GlideImageLoader
import com.ougong.shop.activity.Product.ProductActivity
import com.ougong.shop.activity.ProductList.midle.ProductMidleListActivity
import com.ougong.shop.activity.checkin.CheckInActivity
import com.ougong.shop.activity.combo.FurnitureActivity
import com.ougong.shop.activity.mainHomepage.Search.SearchActivity
import com.ougong.shop.base_mvp.base.BaseFragment
import com.ougong.shop.mtest1.bannerHelper
import com.ougong.shop.utils.RImageLoader
import io.armcha.ribble.presentation.utils.extensions.start
import kotlinx.android.synthetic.main.fragment_homepage_v2.*
import kotlinx.android.synthetic.main.include_back_title.*

class homePageFragmentV2 : BaseFragment<MainHomePageContract.View, MainHomePageContract.Presenter>(),
    MainHomePageContract.View {

    var mBrandIndex = 0
    override fun getAllListSucess(bean: fuckNetbean<CateryBean>) {

        mbean = bean

        homepage_content_catery_left.setText(bean.data[0].child[0].name)

        homepage_content_catery_center.setText(bean.data[0].child[1].name)

        homepage_content_catery_right.setText(bean.data[0].child[2].name)

        for (item in bean.data) {
            if (item.name.startsWith("软装家")) {
                mBrandIndex = bean.data.indexOf(item)
                break
            }
        }

        homepage_content_catery_left1.setText(bean.data[mBrandIndex].child[0].name)

        homepage_content_catery_center1.setText(bean.data[mBrandIndex].child[1].name)

        homepage_content_catery_right1.setText(bean.data[mBrandIndex].child[2].name)

        presenter.getProduct1(mbean!!.data[0].child[0].id)

        presenter.getProduct2(mbean!!.data[mBrandIndex].child[0].id)
    }

    var mbean: fuckNetbean<CateryBean>? = null
//
//    override fun getAllListSucess(bean: fuckNetbean<CateryBean>) {
//        mbean = bean
//
//
//        homepage_content_catery_left.setText(bean.data[0].child[0].name)
//
//        homepage_content_catery_center.setText(bean.data[0].child[1].name)
//
//        homepage_content_catery_right.setText(bean.data[0].child[2].name)
//
//
//        homepage_content_catery_left1.setText(bean.data[1].child[0].name)
//
//        homepage_content_catery_center1.setText(bean.data[1].child[1].name)
//
//        homepage_content_catery_right1.setText(bean.data[1].child[2].name)
//
//
//
//        presenter.getProduct1(mbean!!.data[0].child[0].child[0].id)
//
//        presenter.getProduct2(mbean!!.data[1].child[0].child[0].id)
//
//    }


    override fun getProduceSucess1(bean: Netbean<fuckProcudtList<ProductListItem>>) {
        if (bean.data.total == 0)
            return


        product1 = bean.data
        item1_price.setText(bean.data.rows[0].showPrice?.Fromate())
        item1_name.setText(bean.data.rows[0].title)
        Glide.with(App.app!!)
            .load(bean.data.rows[0].headImage)
            .error(Glide.with(activity).load(R.drawable.catery_child_default))
            .into(item1_image)

//                    holder.view.item2_name.setText(list.get(position).data[1].title)

        if (bean.data.total == 1)
            return

        item2_price.setText(bean.data.rows[1].showPrice?.Fromate())
        item2_name.setText(bean.data.rows[1].title)

        Glide.with(App.app!!)
            .load(bean.data.rows[1].headImage)
            .error(Glide.with(activity).load(R.drawable.catery_child_default))
            .into(item2_image)

        if (bean.data.total == 2)
            return

        item3_price.setText(bean.data.rows[2].showPrice?.Fromate())
        item3_name.setText(bean.data.rows[2].title)

        Glide.with(App.app!!)
            .load(bean.data.rows[2].headImage)
            .error(Glide.with(activity).load(R.drawable.catery_child_default))
            .into(item3_image)


    }


    var product1 : fuckProcudtList<ProductListItem>? = null

    var product2 : fuckProcudtList<ProductListItem>? = null
    override fun getProduceSucess2(bean: Netbean<fuckProcudtList<ProductListItem>>) {

        if (bean.data.total == 0)
            return

        product2 = bean.data

        item1_price1.setText(bean.data.rows[0].showPrice?.Fromate())
        item1_name1.setText(bean.data.rows[0].title)
        Glide.with(App.app!!)
            .load(bean.data.rows[0].headImage)
            .error(Glide.with(activity).load(R.drawable.catery_child_default))
            .into(item1_image1)

        if (bean.data.total == 1)
            return


        item2_price1.setText(bean.data.rows[1].showPrice?.Fromate())
        item2_name1.setText(bean.data.rows[1].title)

        Glide.with(App.app!!)
            .load(bean.data.rows[1].headImage)
            .error(Glide.with(activity).load(R.drawable.catery_child_default))
            .into(item2_image1)

        if (bean.data.total == 2)
            return

        item3_price1.setText(bean.data.rows[2].showPrice?.Fromate())
        item3_name1.setText(bean.data.rows[2].title)

        Glide.with(App.app!!)
            .load(bean.data.rows[2].headImage)
            .error(Glide.with(activity).load(R.drawable.catery_child_default))
            .into(item3_image1)

    }

    override fun initPresenter() = HomepagePresenter()

    override fun setContentViewSource() = R.layout.fragment_homepage_v2


    init {
//        homepagerRecycleAdapter = HomepagerRecycleAdapter(activity)
    }

    override fun initView() {

        search.setOnClickListener {
            activity.start<SearchActivity>()
        }


        homepage_content_catery_left.setOnClickListener {
            mz_banner.viewPager.setCurrentItem(mz_banner.viewPager.currentItem / 3 * 3  )
        }

        homepage_content_catery_center.setOnClickListener {
            mz_banner.viewPager.setCurrentItem(mz_banner.viewPager.currentItem / 3 * 3 + 1 )
        }
        homepage_content_catery_right.setOnClickListener {
            mz_banner.viewPager.setCurrentItem(mz_banner.viewPager.currentItem / 3 * 3 + 2)
        }


        homepage_content_catery_left1.setOnClickListener {
            mz_banner1.viewPager.setCurrentItem(mz_banner1.viewPager.currentItem / 3 * 3  )
        }

        homepage_content_catery_center1.setOnClickListener {
            mz_banner1.viewPager.setCurrentItem(mz_banner1.viewPager.currentItem / 3 * 3 + 1 )
        }
        homepage_content_catery_right1.setOnClickListener {
            mz_banner1.viewPager.setCurrentItem(mz_banner1.viewPager.currentItem / 3 * 3 + 2)
        }


        initLinsetet()
        presenter.getAllList()
        title_name.setText(getString(R.string.home_page_title))

        val list = ArrayList<Int>(3)
        list.add(R.drawable.b1)
        list.add(R.drawable.b2)
        list.add(R.drawable.b3)
        banner.setImages(list)
            .setImageLoader(RImageLoader())
            .start()

        banner.setOnBannerListener {
//            startActivity(Intent(activity,ProductActivity::class.java))
        }

        homepage_content_catery_left.isSelected = true

        mz_banner.addPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                if (mbean == null) {
                    return
                }
                presenter.getProduct1(mbean!!.data[0].child[p0].id)

                if (homepage_content_catery_center == null)
                    return

                homepage_content_catery_left.setSelected(false)
                homepage_content_catery_center.setSelected(false)
                homepage_content_catery_right.setSelected(false)
                if (p0 == 0) {
                    homepage_content_catery_left.setSelected(true)
                } else if (p0 == 1) {
                    homepage_content_catery_center.setSelected(true)
                } else if (p0 == 2) {
                    homepage_content_catery_right.setSelected(true)
                }
            }

        })

        show_more.setOnClickListener {
            mbean?.let {

                if (mbean!!.data[0].child.size <=mz_banner.viewPager.currentItem % 3)
                    return@setOnClickListener
                var intent = Intent(activity, ProductMidleListActivity::class.java)
                intent.putExtra("data", mbean!!.data[0].child[mz_banner.viewPager.currentItem % 3])
                startActivity(intent)
            }

        }


        show_more1.setOnClickListener {
            mbean?.let {
                var intent = Intent(activity, ProductMidleListActivity::class.java)

                if (mbean!!.data[mBrandIndex].child.size <=mz_banner1.viewPager.currentItem % 3)
                    return@setOnClickListener
                intent.putExtra("data", mbean!!.data[mBrandIndex].child[mz_banner1.viewPager.currentItem % 3])
                startActivity(intent)
            }

        }

        homepage_content_catery_left1.isSelected = true

        mz_banner1.addPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {

                if (mbean == null) {
                    return
                }
                presenter.getProduct2(mbean!!.data[mBrandIndex].child[p0].id)

                if (homepage_content_catery_center1 == null) {
                    return
                }
                homepage_content_catery_left1.setSelected(false)
                homepage_content_catery_right1.setSelected(false)
                homepage_content_catery_center1.setSelected(false)
                if (p0 == 0) {
                    homepage_content_catery_left1.setSelected(true)
                } else if (p0 == 1) {
                    homepage_content_catery_center1.setSelected(true)
                } else if (p0 == 2) {
                    homepage_content_catery_right1.setSelected(true)
                }

            }

        })


        bannerHelper.help(mz_banner, 1)

        bannerHelper.help(mz_banner1, 2)


        rl_furniture.setOnClickListener {
            activity.start<FurnitureActivity>()
        }

        rl_checkin.setOnClickListener {
            activity.start<CheckInActivity>()
        }

    }

    private fun initLinsetet() {
        item1.setOnClickListener(this)
        item2.setOnClickListener(this)
        item3.setOnClickListener(this)
        item11.setOnClickListener(this)
        item21.setOnClickListener(this)
        item31.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        var id : ProductListItem? = null
        when(v?.id){
            R.id.item1->{
                id = product1?.rows?.get(0)
            }
            R.id.item2->{
                id = product1?.rows?.get(1)

            }
            R.id.item3->{
                id = product1?.rows?.get(2)

            }
            R.id.item11->{

                id = product2?.rows?.get(0)

            }
            R.id.item21->{

                id = product2?.rows?.get(1)

            }
            R.id.item31->{

                id = product2?.rows?.get(2)

            }
        }

        if (id != null){

            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra(ConstString.PRODUCT_ITEM,id)
            activity.startActivity(intent)
        }
    }

}
