package com.ougong.shop.activity.checkin

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ougong.shop.ActiivtyV2.bean.BrandBeanCollection
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.SxTypeBean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.MsgContract
import com.ougong.shop.R
import com.ougong.shop.httpmodule.StyleBrandBean
import com.ougong.shop.utils.MessageBus
import com.ougong.shop.utils.MessageBusMessage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_sx.*

class SxDialog : DialogFragment(), View.OnClickListener,SxDialogContract.View {
    var brandCategoryId:Int? = null
    companion object {
        var styleId:Int? = null
        var brandId:Int? = null
    }
    fun setBrandCategoryId(brandCategoryId:Int){
        this.brandCategoryId = brandCategoryId
    }
    override fun initBrandList(bean: Netbean<StyleBrandBean>) {
        val array = bean.data.brands!!
        Log.e("array","array = "+array.size)
        gv_pp.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        gv_pp.adapter = BrandAdapter(context,bean.data.brands!!)
        gv_fengge.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        gv_fengge.adapter = StyleAdapter(context,bean.data.styles!!)
    }

    override fun initStyleList(bean: fuckNetbean<BrandBeanCollection.BrandStyleBean>) {
//        gv_fengge.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
//        gv_fengge.adapter = StyleAdapter(context,bean.data)
//        presenter?.getBrandData(brandCategoryId,styleId)
    }

    override fun GotoLogin() {
    }
    var bean:SxTypeBean? = null
    var views:View? = null
    var presenter:SxPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bean = SxTypeBean()
    }
    override fun onStart() {
        super.onStart()
        //设置 dialog 的宽高
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        //设置 dialog 的背景为 null
        dialog.window!!.setBackgroundDrawable(null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        views = inflater.inflate(R.layout.dialog_sx, container, false)
        return views
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = SxPresenter()
        (presenter as SxPresenter).attachLifecycle(lifecycle)
        (presenter as SxPresenter).attachView(this)
        (presenter as SxPresenter).onPresenterCreate()
        setListener()
        initData()
    }


    private fun setListener() {
        iv_close.setOnClickListener{ dismiss() }
        view_dimission.setOnClickListener{ dismiss() }
        tv_reset.setOnClickListener(this)
        tv_commit.setOnClickListener(this)
    }

    private fun initData() {
        gv_pp.setHasFixedSize(true)
        gv_pp.setNestedScrollingEnabled(true)
        gv_fengge.setHasFixedSize(true)
        gv_fengge.setNestedScrollingEnabled(true)
        presenter?.getBrandData(brandCategoryId,null)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_reset   //重置
            -> {
                et_minPrice.setText("")
                et_maxPrice.setText("")
            }
            R.id.tv_commit  //确定
            -> {
                bean?.minPrice = Integer.parseInt(et_minPrice.text.toString().trim())
                bean?.maxPrice = Integer.parseInt(et_maxPrice.text.toString().trim())
                bean?.brandId = brandId
                bean?.styleId = styleId
                MessageBus.getBus().post(MessageBusMessage(MsgContract.RESH_CHECKLIN_PRODUCT_SX,bean))
                dismiss()
            }
        }
    }

    class StyleAdapter(var context: Context?, var data: Array<StyleBrandBean.StyleBean>) : RecyclerView.Adapter<StyleHolder>(){
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): StyleHolder {
            return StyleHolder(LayoutInflater.from(context).inflate(R.layout.item_checklin_all,p0,false))
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(p0: StyleHolder, p1: Int) {
            p0.view?.text = data[p1].name
            p0.view?.setOnClickListener {
                styleId = data[p1].id
            }
        }

    }
    class StyleHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var view:TextView? = null
        init {
            view = itemView as TextView
        }
    }

    class BrandAdapter(var context: Context?, var data: Array<StyleBrandBean.BrandBean>):RecyclerView.Adapter<BrandHolder>(){
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BrandHolder {
            return BrandHolder(LayoutInflater.from(context).inflate(R.layout.item_brand,p0,false) as ImageView)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(p0: BrandHolder, p1: Int) {
            Picasso.get().load(data[p1].logo).into(p0.view)
            p0.itemView.setOnClickListener{
                brandId = data[p1].id
            }
        }


    }
    class BrandHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        var view:ImageView? = null
        init {
            view = itemView as ImageView
        }
    }
}