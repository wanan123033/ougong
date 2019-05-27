package com.ougong.shop.V1

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ougong.shop.R
import kotlinx.android.synthetic.main.homepage_content_item.view.*
import java.util.ArrayList

class HomePageHorizontal (mContext: Context) : RecyclerView.Adapter<HomePageHorizontal.ViewHolder>() {

    private val mProdectList = ArrayList<String>(100)

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    private var inflater: LayoutInflater?

    /**
     * 用于把要展示的数据源传进来，并赋值给一个全局变量mFruitList，我们后续的操作都将在这个数据源的基础上进行。
     * @param mFruitList
     */
    init{
        inflater = LayoutInflater.from(mContext)
  //      LogUtils.v("onBindViewHolder",this)
        for (i in 0..99) {
            mProdectList.add("hello$i")
        }
    }

    /**
     * 用于创建ViewHolder实例的，并把加载出来的布局传入到构造函数当中，最后将viewholder的实例返回
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater?.inflate(R.layout.homepage_content_item, parent, false)
        return ViewHolder(view!!)
    }

    /**
     * 用于对RecyclerView子项的数据进行赋值的，会在每个子项被滚动到屏幕内的时候执行，这里我们通过
     * position参数得到当前项的Fruit实例，然后再将数据设置到ViewHolder的Imageview和textview当中即可，
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = mProdectList[position]
//        holder.view.home_read_piv_iv.setImageResource()
        holder.view.home_read_title.text = fruit

    }


    override fun getItemCount(): Int {
        return mProdectList.size
    }
}