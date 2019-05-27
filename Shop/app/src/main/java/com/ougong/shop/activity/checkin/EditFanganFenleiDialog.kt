package com.ougong.shop.activity.checkin

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import com.ougong.shop.Bean.CateryBean
import com.ougong.shop.Bean.fuckNetbean
import com.ougong.shop.MsgContract
import com.ougong.shop.R
import com.ougong.shop.activity.CheckLinData
import com.ougong.shop.adapter.ThreeCategoryAdapter
import com.ougong.shop.utils.MessageBus
import com.ougong.shop.utils.MessageBusMessage
import kotlinx.android.synthetic.main.dialog_edit_fanganfenlei.*


class EditFanganFenleiDialog : DialogFragment(), View.OnClickListener,EditFanganFenleiContract.View,
    AdapterView.OnItemClickListener {
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //TODO 修改分类状态 是否选中
        val cate = (gv_category.adapter as ThreeCategoryAdapter).getItem(position)
        for (oneCate in category!!.data){
            for (twoCate in oneCate.child){
                for (threeCate in twoCate.child){
                    if (threeCate.name.equals(cate.name)){
                        threeCate.enabless = !threeCate.enabless
                    }
                }
            }
        }
        (gv_category.adapter as ThreeCategoryAdapter).notifyDataSetChanged()
    }

    var selectedCategorys:ArrayList<CheckLinData.CategoryBean>? = arrayListOf()
    var category:fuckNetbean<CateryBean>? = null
    var roomId:Int? = null
    private var roomName:String? = null
    override fun initOneCategory(bean: fuckNetbean<CateryBean>) {
        this.category = bean
        if (selectedCategorys != null) {
            for (selectedCategory in selectedCategorys!!){
                for (oneCate in bean.data){
                    for (twoCate in oneCate.child){
                        for (threeCate in twoCate.child){
                            threeCate.enabless = selectedCategory.categoryName.equals(threeCate.name)
                        }
                    }
                }
            }
        }
        (rv_pp_fenlei.adapter as PPAdapter).setData(bean.data)
    }

    var presenter:EditFanganFenleiContract.Presenter? = null

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_reset ->{
                dismiss()
            }
            R.id.tv_commit ->{
                CheckLinUtils.getInstance().resetCateGory(category?.data,roomId!!,roomName)
                dismiss()
                //发送消息通知分类信息重新显示分类信息列表
                MessageBus.getBus().post(MessageBusMessage(MsgContract.RESH_CHECKLIN_ROOM_CATEGORY,roomId!!))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        //设置 dialog 的宽高
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        //设置 dialog 的背景为 null
        dialog.window!!.setBackgroundDrawable(null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.dialog_edit_fanganfenlei,container,false)
    }

    fun setRoomName(roomName:String){
        this.roomName = roomName
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = EditFanganPresenter()
        (presenter as EditFanganPresenter).attachLifecycle(lifecycle)
        (presenter as EditFanganPresenter).attachView(this)
        (presenter as EditFanganPresenter).onPresenterCreate()
        initView()
        setListener()
        initData()
    }

    fun initView(){
        rv_pp_fenlei.layoutManager = LinearLayoutManager(context)
        rv_pp_fenlei.adapter = PPAdapter(context!!,this)
        gv_category.adapter = ThreeCategoryAdapter(context)
        presenter?.getAllOneCategory()

        if (selectedCategorys!= null){
        }
    }
    fun setListener(){
        gv_category.onItemClickListener = this
    }
    fun initData(){
        iv_close.setOnClickListener {
            dismiss()
        }
        view_dismiss.setOnClickListener {
            dismiss()
        }
        tv_reset.setOnClickListener(this)
        tv_commit.setOnClickListener(this)
    }

    class PPAdapter(var context:Context,var fragment:Fragment) : RecyclerView.Adapter<PPHolder>(){
        var datas:Array<CateryBean>? = null
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PPHolder {
            return PPHolder(LayoutInflater.from(context).inflate(R.layout.item_pp,p0,false),fragment)
        }

        override fun getItemCount(): Int {
            if (datas == null)
                return 0
            return datas!!.size
        }

        override fun onBindViewHolder(holder: PPHolder, p1: Int) {
            holder.setData(datas!![p1])
            if (p1 == 0){
                holder.rv_hx?.visibility = View.VISIBLE
            }else{
                holder.rv_hx?.visibility = View.GONE
            }
        }

        fun setData(data: Array<CateryBean>) {
            datas = data
            notifyDataSetChanged()
        }
    }
    class PPHolder(itemView:View,fragment: Fragment) : RecyclerView.ViewHolder(itemView){
        var rv_hx:RecyclerView? = null
        var tv_name:TextView? = null
        init {
            tv_name = itemView.findViewById(R.id.tv_name)
            rv_hx = itemView.findViewById(R.id.rv_hx)
            rv_hx?.layoutManager = LinearLayoutManager(itemView.context)
            rv_hx?.adapter = TwoCategoryAdapter(itemView.context,fragment)
        }
        fun setData(datas: CateryBean) {
            tv_name?.text = datas.name
            datas.child[0].enabless = true
            (rv_hx?.adapter as TwoCategoryAdapter).setData(datas.child)

            tv_name?.setOnClickListener {
                if (rv_hx?.visibility == View.VISIBLE){
                    rv_hx?.visibility = View.GONE
                }else{
                    rv_hx?.visibility = View.VISIBLE
                }
            }
        }

    }

    class TwoCategoryAdapter(var context: Context,var fragment: Fragment) : RecyclerView.Adapter<TwoCategoryViewHolder>(){
        var child: Array<CateryBean>? = null
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TwoCategoryViewHolder {
            return TwoCategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cate,p0,false),fragment)
        }

        override fun getItemCount(): Int {
            if (child == null){
                return 0
            }
            return child!!.size
        }

        override fun onBindViewHolder(holder: TwoCategoryViewHolder, p1: Int) {

            holder.initData(child!![p1], child!!,this)
            if (p1 == 0){
                (fragment as EditFanganFenleiDialog).onReshGrid(child!![p1].child)
            }

        }

        fun setData(child: Array<CateryBean>) {
            this.child = child
            notifyDataSetChanged()
        }

    }
    class TwoCategoryViewHolder(itemView: View,var fragment: Fragment) : RecyclerView.ViewHolder(itemView){

        fun initData(
            cateryBean: CateryBean,
            child: Array<CateryBean>,
            twoCategoryAdapter: TwoCategoryAdapter
        ) {
            (itemView as TextView).text = cateryBean.name
            if (cateryBean.enabless){
                (itemView as TextView).setBackgroundColor(Color.BLACK)
                (itemView as TextView).setTextColor(Color.WHITE)
                (fragment as EditFanganFenleiDialog).onReshGrid(cateryBean.child)
            }else{
                (itemView as TextView).setBackgroundResource(R.drawable.recy_item_ff)
                (itemView as TextView).setTextColor(Color.BLACK)
            }
            itemView.setOnClickListener {
                for (bean in child){
                    bean.enabless = bean == cateryBean
                }
                twoCategoryAdapter.notifyDataSetChanged()
            }
        }



    }
    fun onReshGrid(child: Array<CateryBean>) {
        (gv_category.adapter as ThreeCategoryAdapter).setData(child)
        val cates = (gv_category.adapter as ThreeCategoryAdapter).cates
        for (cate in cates){
            for (num in 0..(selectedCategorys!!.size - 1)){
                if(selectedCategorys!![num].categoryName.equals(cate.name)){
                    cate.enabless = true
                    break
                }else{
                    cate.enabless = false
                }
            }
        }
        (gv_category.adapter as ThreeCategoryAdapter).notifyDataSetChanged()
    }
    override fun GotoLogin() {
    }

    fun setSelectedCategory(data: List<CheckLinData.CategoryBean>,roomId:Int) {
        selectedCategorys?.clear()
        selectedCategorys?.addAll(data)
        this.roomId = roomId
    }


}