package com.baigui.commonview.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.baigui.commonview.R
import com.baigui.commonview.bean.DataListTree
import com.baigui.commonview.bean.ItemStatus

import java.util.ArrayList

/**
 * **************************************************
 * ******************_ooOoo_
 * *****************o8888888o
 * *****************88\" . \"88
 * *****************(| -_-  |)
 * *****************O\\  =  /O
 * **************____/`---'\\____
 * ************.'  \\\\|     |//  `.
 * ~~~~~~~~~~~/  \\\\|||  :  |||//  \\
 * ~~~~~~~~~~~/  _||||| -:- |||||-  \\
 * ~~~~~~~~~~~|   | \\\\\\  -  /// |   |
 * ~~~~~~~~~~~| \\_|  ''\\---/''  |   |
 * ~~~~~~~~~~~\\  .-\\__  `-`  ___/-. /
 * ~~~~~~~~~___`. .'  /--.--\\  `. . __
 * ~~~~~~.\"\" '<  `.___\\_<|>_/___.'  >'\"\".
 * ~~~~~| | :  `- \\`.;`\\ _ /`;.`/ - ` : | |
 * ~~~~~\\  \\ `-.   \\_ __\\ /__ _/   .-` /  /
 * ======`-.____`-.___\\_____/___.-`____.-'======
 * ******************`=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"
 * ***************佛祖保佑*********永无BUG
 *
 * @ 项目名称: rkhy.com.ecg.view.ExpandableRecyclerView
 * @ 日        期:2018/4/18 17:21
 * @ 作        者:shangming
 * **************************************************
 */
class ExpandableRecyclerViewAdapter(private val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mDataListTrees: ArrayList<DataListTree<String, String>> = ArrayList(3)
    private var mGroupItemStatus: MutableList<Boolean>? = null // 保存一级标题的开关状态


    init {
        var mlis1: ArrayList<String> = ArrayList(9)
        for (i in 1..80) {
            mlis1.add("hello")

        }
        mDataListTrees.add(DataListTree("biaoti",mlis1))
//        mDataListTrees.add(DataListTree("biaoti",mlis1))
//        mDataListTrees.add(DataListTree("biaoti",mlis1))

        initGroupItemStatus()
    }
    /**
     * 设置显示的数据
     *
     * @param dataListTrees
     */
    fun setData(dataListTrees: ArrayList<DataListTree<String, String>>) {
        this.mDataListTrees = dataListTrees

        initGroupItemStatus()
        notifyDataSetChanged()
    }

    /**
     * 初始化一级列表开关状态
     */
    private fun initGroupItemStatus() {
        mGroupItemStatus = ArrayList()
        for (i in mDataListTrees!!.indices) {
            mGroupItemStatus!!.add(false)
        }
    }

    /**
     * 根据item的位置，获取当前Item的状态
     *
     * @param position 当前item的位置（此position的计数包含groupItem和subItem合计）
     * @return 当前Item的状态（此Item可能是groupItem，也可能是SubItem）
     */
    private fun getItemStatusByPosition(position: Int): ItemStatus {
        val itemStatus = ItemStatus()
        var itemCount = 0
        var i: Int
        //轮询 groupItem 的开关状态
        i = 0
        while (i < mGroupItemStatus!!.size) {
            if (itemCount == position) { //position刚好等于计数时，item为groupItem
                itemStatus.viewType = ItemStatus.VIEW_TYPE_GROUP_ITEM
                itemStatus.groupItemIndex = i
                break
            } else if (itemCount > position) { //position大于计数时，item为groupItem(i - 1)中的某个subItem
                itemStatus.viewType = ItemStatus.VIEW_TYPE_SUB_ITEM
                itemStatus.groupItemIndex = i - 1 // 指定的position组索引
                // 计算指定的position前，统计的列表项和
                val temp = itemCount - mDataListTrees!![i - 1].subItem.size
                // 指定的position的子项索引：即为position-之前统计的列表项和
                itemStatus.subItemIndex = position - temp
                break
            }

            itemCount++
            if (mGroupItemStatus!![i]) {
                itemCount += mDataListTrees!![i].subItem.size
            }
            i++
        }
        // 轮询到最后一组时，未找到对应位置
        if (i >= mGroupItemStatus!!.size) {
            itemStatus.viewType = ItemStatus.VIEW_TYPE_SUB_ITEM // 设置为二级标签类型
            itemStatus.groupItemIndex = (i - 1) // 设置一级标签为最后一组
            itemStatus.subItemIndex = (position - (itemCount - mDataListTrees!![i - 1].subItem.size))
        }
        return itemStatus
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        var viewHolder: RecyclerView.ViewHolder
        if (viewType == ItemStatus.VIEW_TYPE_GROUP_ITEM) {
            // parent需要传入对应的位置，否则列表项触发不了点击事件
            view = LayoutInflater.from(mContext).inflate(R.layout.expandalbe_group_item, parent, false)
            viewHolder = GroupItemViewHolder(view)
        } else{
            view = LayoutInflater.from(mContext).inflate(R.layout.expandalbe_sub_item, parent, false)
            viewHolder = SubItemViewHolder(view)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemStatus = getItemStatusByPosition(position) // 获取列表项状态
        val data = mDataListTrees!![itemStatus.groupItemIndex]

        if (itemStatus.viewType === ItemStatus.VIEW_TYPE_GROUP_ITEM) { // 组类型
            val groupItemViewHolder = holder as GroupItemViewHolder
//            groupItemViewHolder.view.text = data.groupItem

            val groupIndex = itemStatus.groupItemIndex // 组索引
            groupItemViewHolder.itemView.setOnClickListener {
                if (mGroupItemStatus!![groupIndex]) { // 一级标题打开状态
                    mGroupItemStatus!![groupIndex] = false
                    notifyItemRangeRemoved(groupItemViewHolder.adapterPosition + 1, data.subItem.size)
                } else { // 一级标题关闭状态
                    initGroupItemStatus() // 1. 实现只展开一个组的功能，缺点是没有动画效果
                    mGroupItemStatus!![groupIndex] = true
                    notifyDataSetChanged() // 1. 实现只展开一个组的功能，缺点是没有动画效果
                    //                    notifyItemRangeInserted(groupItemViewHolder.getAdapterPosition() + 1, data.getSubItem().size()); // 2. 实现展开可多个组的功能，带动画效果
                }
            }
        } else if (itemStatus.viewType === ItemStatus.VIEW_TYPE_SUB_ITEM) { // 子项类型
            val subItemViewHolder = holder as SubItemViewHolder
//            subItemViewHolder.mSubItemTitle.text = data.subItem.get(itemStatus.subItemIndex)
            subItemViewHolder.itemView.setOnClickListener { v ->
//                ToastUtil.makeText(
//                    mContext,
//                    mDataListTrees!![itemStatus.getGroupItemIndex()].getSubItem().get(itemStatus.getSubItemIndex()),
//                    Toast.LENGTH_SHORT
//                ).show()
            }
        }
    }

    override fun getItemCount(): Int {
        var itemCount = 0

        if (0 == mGroupItemStatus!!.size) {
            return itemCount
        }

        for (i in mDataListTrees!!.indices) {
            itemCount++ // 每个一级标题项+1
            if (mGroupItemStatus!![i]) { // 二级标题展开时，再加上二级标题的数量
                itemCount += mDataListTrees!![i].subItem.size
            }
        }
        return itemCount
    }

    override fun getItemViewType(position: Int): Int {
        return getItemStatusByPosition(position).viewType
    }

    /**
     * 组项ViewHolder
     */
    internal class GroupItemViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    /**
     * 子项ViewHolder
     */
    internal class SubItemViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}