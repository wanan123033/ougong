package com.ougong.shop.activity.checkin

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.baigui.commonlib.utils.ToastUtils
import com.bumptech.glide.Glide
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.ougong.shop.App
import com.ougong.shop.Bean.Netbean
import com.ougong.shop.Bean.SxTypeBean
import com.ougong.shop.MsgContract
import com.ougong.shop.R
import com.ougong.shop.activity.CheckLinData
import com.ougong.shop.base_mvp.base.BaseFragment
import com.ougong.shop.httpmodule.HxSpaceBean
import com.ougong.shop.httpmodule.ProductCheckLinBean
import com.ougong.shop.utils.MessageBus
import com.ougong.shop.utils.MessageBusMessage
import kotlinx.android.synthetic.main.dialog_sx.*
import kotlinx.android.synthetic.main.frag_check_in.*

class CheckInFanganFragment : BaseFragment<CheckInFanganFragmentContract.View,CheckInFanganFragmentContract.Presenter>(),CheckInFanganFragmentContract.View,
    AdapterView.OnItemClickListener,View.OnClickListener {
    override fun initProducts(bean: Netbean<ProductCheckLinBean>) {
        if (bean.data.rows == null || bean.data.rows.isNullOrEmpty()){
            rl_menu.visibility = View.GONE
            rv_product_shop.visibility = View.GONE
            inclue_footer.visibility = View.VISIBLE
        }else{
            rl_menu.visibility = View.VISIBLE
            rv_product_shop.visibility = View.VISIBLE
            inclue_footer.visibility = View.GONE
        }

        if (mPageNum == 1){
            (rv_product_shop.adapter as ProductShopAdapter).clearData()
        }
        val rows = bean.data.rows
        (rv_product_shop.adapter as ProductShopAdapter).addData(rows)
    }


    override fun setContentViewSource() = R.layout.frag_check_in

    override fun initPresenter() = CheckInFanganFragPresenter()
    var hx:HxSpaceBean? = null
    var category: CheckLinData.CategoryBean? = null
    var roomId:Int? = 0
    var mPageNum:Int = 0
    var mSort:String = "view_count"  // view_count 最热门  create_time: 最新  price 价格
    var asc:String = "asc" //asc  升序  desc 降序
    var roomName:String? = null

    var brandId:Int? = null
    var styleId:Int? = null
    var minPrice:Int? = null
    var maxPrice:Int? = null
    override fun initView() {
        super.initView()
        rv_product_shop.layoutManager = LinearLayoutManager(context)
        rv_product_shop.adapter = ProductShopAdapter(activity,this)
        gv_fenlei.adapter = FenleiAdapter(context)

        tv_sx.setOnClickListener {
            val dialog = SxDialog()
            dialog.setBrandCategoryId(category!!.categoryId)
            dialog.show(activity.getSupportFragmentManager(),"SxDialog")
        }

        gv_fenlei.onItemClickListener = this
        val cateByRoom = CheckLinUtils.getInstance().getCateByRoom(hx!!.name)
        if (cateByRoom != null && !cateByRoom.isEmpty())
            cateByRoom[0].enable = true
        (gv_fenlei.adapter as FenleiAdapter).data = cateByRoom
        rl_menu.visibility = View.GONE
        rv_product_shop.visibility = View.GONE
        inclue_footer.visibility = View.VISIBLE
        tv_hot.setOnClickListener(this)
        tv_new.setOnClickListener(this)
        tv_price.setOnClickListener(this)

        rv_product_shop.setPullRefreshEnabled(true)
        rv_product_shop.setLoadingMoreEnabled(true)
        rv_product_shop.getDefaultRefreshHeaderView().setRefreshTimeVisible(true)
        rv_product_shop.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
        rv_product_shop.setLoadingMoreProgressStyle(ProgressStyle.BallRotate)
        rv_product_shop.setLoadingListener(object :XRecyclerView.LoadingListener{
            override fun onRefresh() {
                mPageNum = 1
                presenter.getProducts(brandId,category!!.categoryId,18,maxPrice,minPrice,asc,mPageNum,mSort,styleId)
            }

            override fun onLoadMore() {
                mPageNum++
                presenter.getProducts(brandId,category!!.categoryId,18,maxPrice,minPrice,asc,mPageNum,mSort,styleId)

            }

        })
        if (cateByRoom == null || cateByRoom.isEmpty()){
            return
        }
        category = cateByRoom[0]
        presenter.getProducts(null,category!!.categoryId,18,null,null,asc,1,mSort,null)

        rv_product_shop.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (gv_fenlei.visibility != View.GONE){
                    gv_fenlei.visibility = View.GONE
                    isGrid = true
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (isGrid){
            gv_fenlei.visibility = View.VISIBLE
        }else{
            gv_fenlei.visibility = View.GONE
        }
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_hot ->{
                tv_hot.setTextColor(Color.BLACK)
                tv_new.setTextColor(Color.parseColor("#999999"))
                tv_name.setTextColor(Color.parseColor("#999999"))
                mPageNum = 1
                mSort = "view_count"
                presenter.getProducts(null,category!!.categoryId,18,null,null,asc,mPageNum,mSort,null)
            }
            R.id.tv_new ->{
                tv_new.setTextColor(Color.BLACK)
                tv_hot.setTextColor(Color.parseColor("#999999"))
                tv_name.setTextColor(Color.parseColor("#999999"))
                mSort = "create_time"
                mPageNum = 1
                presenter.getProducts(null,category!!.categoryId,18,null,null,asc,mPageNum,mSort,null)
            }
            R.id.tv_price ->{
                if (asc == "asc"){
                    asc = "desc"
                }else{
                    asc = "asc"
                }
                tv_name.setTextColor(Color.BLACK)
                tv_hot.setTextColor(Color.parseColor("#999999"))
                tv_new.setTextColor(Color.parseColor("#999999"))
                mPageNum = 1
                mSort = "price"
                presenter.getProducts(null,category!!.categoryId,18,null,null,asc,mPageNum,mSort,null)

            }
        }
    }
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //TODO 获取产品信息列表
        mPageNum = 1
        val data = (gv_fenlei.adapter as FenleiAdapter).data
        if (position == data.size){  //添加分类
            val dialog = EditFanganFenleiDialog()
            dialog.setRoomName(roomName!!)
            //TODO 设置已选中的分类
            dialog.setSelectedCategory((gv_fenlei.adapter as FenleiAdapter).data,roomId!!)
            dialog.show(activity.getSupportFragmentManager(),"EditFanganFenleiDialog")
            return
        }else{
            for (dataIndex in 0 .. (data.size - 1)){
                data[dataIndex].enable = false
            }
            data[position].enable = true
            (gv_fenlei.adapter as BaseAdapter).notifyDataSetChanged()
        }
        gv_fenlei.visibility = View.GONE
        category = data[position]
        presenter.getProducts(null,data[position].categoryId,18,null,null,asc,mPageNum,mSort,null)
    }
    override fun initData() {
        super.initData()
    }
    fun setData(hx: HxSpaceBean,roomId:Int,roomName:String): CheckInFanganFragment {
        this.hx = hx
        this.roomId = roomId
        this.roomName = roomName
        return this
    }
    fun updateSpecData(specifications: Array<ProductCheckLinBean.SpecDataBean>) {
        (rv_product_shop.adapter as ProductShopAdapter).setSpecData(specifications)
    }

    class ProductShopAdapter(var context:Context,var fragment:CheckInFanganFragment?) : RecyclerView.Adapter<ProductShopHolder>(){
        var datas:ArrayList<ProductCheckLinBean.DataBean>? = arrayListOf()
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductShopHolder {
            return ProductShopHolder(LayoutInflater.from(context).inflate(R.layout.item_product_shop,p0,false),context,this)
        }

        override fun getItemCount(): Int {
            return datas!!.size
        }

        override fun onBindViewHolder(holder: ProductShopHolder, p1: Int) {
            holder.setData(datas!![p1])
        }

        fun addData(rows: Array<ProductCheckLinBean.DataBean>?) {
            for (i in 0 .. (rows!!.size - 1)){
                datas?.add(rows[i])
            }
            notifyDataSetChanged()
        }

        fun setSpecData(specifications: Array<ProductCheckLinBean.SpecDataBean>){
            for (data in 0..(datas!!.size - 1)) {
                if (specifications[0].productId == datas!![data].id) {
                    datas!![data].specifications = specifications
                    break
                }
            }
            notifyDataSetChanged()
        }

        fun clearData() {
            datas?.clear()
            notifyDataSetChanged()
        }
    }
    class ProductShopHolder(view: View,var context: Context,var adapter:ProductShopAdapter) : RecyclerView.ViewHolder(view), View.OnClickListener {

        var iv_img:ImageView? = null
        var tv_name:TextView? = null
        var tv_price:TextView? = null
        var tv_spec:TextView? = null
        var itemData:ProductCheckLinBean.DataBean? = null
        init {
            iv_img = view.findViewById(R.id.iv_img)
            tv_name = view.findViewById(R.id.tv_name)
            tv_price = view.findViewById(R.id.tv_price)
            tv_spec = view.findViewById(R.id.tv_spec)
            view.findViewById<View>(R.id.rl_ge).setOnClickListener(this)
            view.findViewById<View>(R.id.tv_insert_sp).setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when(v!!.id){
                R.id.rl_ge -> { //选择规格
                    val dialog = MyShopSpDialog()
                    dialog.setData(itemData?.specifications)
                    dialog.show((context as AppCompatActivity).supportFragmentManager,"MyShopSpDialog")
                }

                R.id.tv_insert_sp -> { //加入配置单
                    adapter.fragment!!.addProduct(itemData)
                    MessageBus.getBus().post(MessageBusMessage(MsgContract.RESH_CHECKLIN_ROOM_NUM_PRICE))
//                    CheckLinUtils.getInstance().addProduct()
                }
            }
        }
        fun setData(data:ProductCheckLinBean.DataBean){
            this.itemData = data
            tv_name?.text = data.title
            tv_price?.text = "￥"+String.format("%.2f",data.specifications[0].price)
            Glide.with(App.app!!).load(data.headImage).into(iv_img!!)

            val specifications = data.specifications
            for (spec in specifications){
                if (spec.isDefault){
                    tv_price?.text = "￥"+ String.format("%.2f",spec.price)
                    tv_spec?.text = "选择："+ spec.color+"/"+spec.spec
                    break
                }
            }
        }
    }

    private fun addProduct(itemData: ProductCheckLinBean.DataBean?) {
        val data : CheckLinData.ProductBean? = CheckLinData.ProductBean()
        data?.productId = itemData?.id
        data?.productName = itemData?.title
        for (item in 0..(itemData?.specifications?.size!!-1)){
            if (itemData?.specifications!![item].isDefault){
                data?.price = itemData?.specifications!![item].price
                data?.color = itemData?.specifications!![item].color
                data?.spec = itemData?.specifications!![item].spec
                data?.specId = itemData?.specifications!![item].id
                data?.headImage = itemData?.headImage
            }
        }
        CheckLinUtils.getInstance().addProduct(hx,category,data!!)
        ToastUtils.toast(activity,"加入配置单成功")
    }

    fun reshGv() {
        val cateByRoom = CheckLinUtils.getInstance().getCateByRoom(hx!!.name)
        (gv_fenlei.adapter as FenleiAdapter).data = cateByRoom
    }
    var isGrid:Boolean = true
    fun initGridView() {
        if (gv_fenlei != null)
            isGrid = gv_fenlei.visibility == View.GONE
        if (isVisible){
            if (isGrid){
                gv_fenlei.visibility = View.VISIBLE
                isGrid = true
            }else{
                gv_fenlei.visibility = View.GONE
            }
        }
    }

    fun reshProductData(bean: SxTypeBean) {
        mPageNum = 1
        brandId = bean.brandId
        styleId = bean.styleId
        minPrice = bean.minPrice
        maxPrice = bean.maxPrice
        presenter.getProducts(brandId,category!!.categoryId,18,maxPrice,minPrice,asc,mPageNum,mSort,styleId)
    }
}