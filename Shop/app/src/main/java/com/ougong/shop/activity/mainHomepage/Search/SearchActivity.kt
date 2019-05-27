package com.ougong.shop.activity.mainHomepage.Search

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import com.baigui.commonlib.kotlinUtils.StarusBarUtils
import com.baigui.commonlib.utils.LogUtils
import com.baigui.commonlib.utils.ScreenUtils
import com.baigui.commonlib.utils.SoftKeyBoardUtils
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.ProductListItem
import com.ougong.shop.Bean.fuckProcudtList
import com.ougong.shop.R
import com.ougong.shop.activity.ProductList.bottom.ProductListAdapter
import com.ougong.shop.base_mvp.base.BaseActivity
import io.armcha.ribble.presentation.utils.extensions.addBottomMargin
import io.armcha.ribble.presentation.utils.extensions.addTopMargin
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity<SearchContract.View, SearchContract.Presenter>(),SearchContract.View {

    override fun getProductSucess(it: Netbean<fuckProcudtList<ProductListItem>>) {

        if (mPage == 1 && it.data.rows.isEmpty()){
            showEmpty(true)
            return
        }

        mPage++
        mList.addAll(it.data.rows)

        mAdapter?.notifyDataSetChanged()
        recycleView.refreshComplete()
        if (it.data.rows.size < 10){
//            recycleView.setLoadingMoreEnabled(false)
//            val te = TextView(this)
//            te.setText("没有更多了！")
            recycleView.footView.addTopMargin(ScreenUtils.dipTopx(this,20f))
            recycleView.footView.addBottomMargin(ScreenUtils.dipTopx(this,20f))

            recycleView.setNoMore(true)
            recycleView.setFootViewText("正在加载","没有更多了！")
        }

    }

    private fun showEmpty(b: Boolean) {
        if (b){
            usless1.visibility = View.VISIBLE
//            empty_text.visibility = View.VISIBLE
        }else{
            usless1.visibility = View.GONE
//            empty_text.visibility = View.GONE
        }
    }

    override fun setContentViewSource() = R.layout.activity_search

    override fun initPresenter() = SearchPresenter()

    override fun initView() {

        StarusBarUtils.setStatusBarColor(this, R.color.white)
        StarusBarUtils.setAndroidNativeLightStatusBar(this, true)
        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(TextUtils.isEmpty(s.toString().trim())){
                    operate.setText("取消")
                    delect.visibility = View.INVISIBLE
                }else{
                    operate.setText("搜索")
                    delect.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        delect.setOnClickListener {
            search.setText("")
            showEmpty(false)
            mList.clear()
            mAdapter?.notifyDataSetChanged()
        }
        var mCurrent : String? = null
        operate.setOnClickListener {
            if(TextUtils.isEmpty(search.text.toString().trim())){
                finish()
            }else{
                SoftKeyBoardUtils.closeKeybord(search,this)
                mPage = 1

                mCurrent = search.text.toString()
                mList.clear()

                recycleView.setNoMore(false)
                mAdapter?.notifyDataSetChanged()
                showEmpty(false)
                presenter.getProduct(mCurrent!!,1)
            }
        }

        mAdapter = SearchAdapter(this,mList)

        recycleView.adapter = mAdapter
        recycleView.setPullRefreshEnabled(false)
        recycleView.setLayoutManager(LinearLayoutManager(this))


        recycleView.setLoadingMoreEnabled(true)

        recycleView.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onLoadMore() {
                    presenter.getProduct(mCurrent!!,page = mPage)

            }
            override fun onRefresh() {
            }
        })

    }

    var mList = arrayListOf<ProductListItem>()
    var mAdapter: SearchAdapter? = null

    var mPage = 1
}
