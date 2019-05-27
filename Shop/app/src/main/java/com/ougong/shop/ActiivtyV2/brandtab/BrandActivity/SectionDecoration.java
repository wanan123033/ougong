package com.ougong.shop.ActiivtyV2.brandtab.BrandActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.baigui.commonlib.utils.ScreenUtils;


/**
 * @author byd666
 * @date 2017/6/15
 */

public class SectionDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "SectionDecoration";

    private int mGroupHeight = 80;
    /**
     * 是否靠左边
     */
    private boolean isAlignLeft = true;

    public SectionDecoration(int height){
        mGroupHeight = height;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        String groupId = getGroupName(pos);
        if (groupId == null) {
            return;
        }
        //只有是同一组的第一个才显示悬浮栏
        if (isFirstInGroup(pos)) {
            outRect.top = mGroupHeight;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        //获取单条的数目
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        //得出距离左边和右边的padding
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        //开始绘制   有可能是从下向上滑动，，，
        String preGroupName;
        String currentGroupName = null;

        Log.e("--------------",""+itemCount+"-----"+childCount);
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            preGroupName = currentGroupName;
            currentGroupName = getGroupName(position);
            if ((TextUtils.equals(currentGroupName, preGroupName) || position == 0 || position == 1) && position != 2) {  //为什么current和pre会相等呢？？也许是两个重合和
                continue;
            }

            Log.e("------------------",""+position);
//
//            if (position == 0 || position > 1)
//                break;
            int viewBottom = view.getBottom();
            //top 决定当前顶部第一个悬浮Group的位置
            int top = Math.max(mGroupHeight, view.getTop());
//            if (position + 1 < itemCount) {  //itemc
//                //获取下个GroupName
//                String nextGroupName = getGroupName(position + 1);
//                //下一组的第一个View接近头部
//                if (!currentGroupName.equals(nextGroupName) && viewBottom < top) {
//                    top = viewBottom;
//                }
//            }

            //根据position获取View
            View groupView = getTabView(position);
            if (groupView == null) {
                return;
            }
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mGroupHeight);
            groupView.setLayoutParams(layoutParams);
            groupView.setDrawingCacheEnabled(true);
            groupView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            //指定高度、宽度的groupView
            groupView.layout(0, 0, right, mGroupHeight);//zhe里是吧group直接给放在最开始的位置，
            groupView.buildDrawingCache();
            Bitmap bitmap = groupView.getDrawingCache();
            int marginLeft = isAlignLeft ? 0 : right - groupView.getMeasuredWidth();
            c.drawBitmap(bitmap, left + marginLeft, top - mGroupHeight, null);
        }
    }

    private View getTabView(int position) {
        return TabView;
    }

    private void setTabView(View v) {
        TabView = v;
    }


    View TabView = null;
    /**
     * 判断是不是组中的第一个位置
     * 根据前一个组名，判断当前是否为新的组
     */
    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return false;
        } else if ( pos == 2){
            return true;
        } else {
            return false;
//            String prevGroupId = getGroupName(pos - 1);
//            String groupId = getGroupName(pos);
//            return !TextUtils.equals(prevGroupId, groupId);
        }
    }

    /**
     * 获取组名
     *
     * @param position
     * @return 组名
     */
    private String getGroupName(int position) {
        return ""+position/Integer.MAX_VALUE;
//        if (mGroupListener != null) {
//            return mGroupListener.getGroupName(position);
//        } else {
//            return null;
//        }
    }

    public static class Builder {
        SectionDecoration mDecoration;


        /**
         * 设置Group高度
         *
         * @param groutHeight 高度
         * @return this
         */
        public Builder setGroupHeight(int groutHeight) {
            mDecoration.mGroupHeight = groutHeight;
            return this;
        }

        /**
         * 是否靠左边
         * true 靠左边（默认）、false 靠右边
         *
         * @param b b
         * @return this
         */
        public Builder isAlignLeft(boolean b) {
            mDecoration.isAlignLeft = b;
            return this;
        }

        public SectionDecoration build() {
            return mDecoration;
        }
    }

}
