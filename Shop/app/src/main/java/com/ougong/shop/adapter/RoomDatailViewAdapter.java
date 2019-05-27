package com.ougong.shop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ougong.shop.App;
import com.ougong.shop.MsgContract;
import com.ougong.shop.R;
import com.ougong.shop.httpmodule.ComboDatailBean;
import com.ougong.shop.httpmodule.RoomProductBean;
import com.ougong.shop.utils.MessageBus;
import com.ougong.shop.utils.MessageBusMessage;

public class RoomDatailViewAdapter extends RecyclerView.Adapter<RoomDatailViewAdapter.RoomDatailViewHolder> {

    private boolean isGuding;
    private ComboDatailBean.Room room;
    private ComboDatailBean.Product[] product;
    private Context mContext;

    public RoomDatailViewAdapter(Context context, ComboDatailBean.Product[] product, ComboDatailBean.Room room,boolean isGuding) {
        this.mContext = context;
        this.product = product;
        this.room = room;
        this.isGuding = isGuding;
    }

    @NonNull
    @Override
    public RoomDatailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_room_datail_item,viewGroup,false);
        return new RoomDatailViewHolder(view,room,isGuding);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomDatailViewHolder roomDatailViewHolder, int i) {
        roomDatailViewHolder.setData(product[i],i,product.length == 1);

    }

    @Override
    public int getItemCount() {
        return product.length;
    }

    static class RoomDatailViewHolder extends RecyclerView.ViewHolder{
        private boolean isGuding;
        private ComboDatailBean.Room room;
        ImageView iv_img;
        TextView tv_name,tv_spec,tv_color,tv_sum,tv_pos,tv_price;
        RelativeLayout rl_haha;

        public RoomDatailViewHolder(@NonNull View itemView, ComboDatailBean.Room room, boolean isGuding) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_spec = itemView.findViewById(R.id.tv_spec);
            tv_color = itemView.findViewById(R.id.tv_color);
            tv_sum = itemView.findViewById(R.id.tv_sum);
            tv_pos = itemView.findViewById(R.id.tv_pos);
            rl_haha = itemView.findViewById(R.id.rl_haha);
            tv_price = itemView.findViewById(R.id.tv_price);
            this.room = room;
            this.isGuding = isGuding;
        }

        public void setData(final ComboDatailBean.Product product, int i, boolean b) {
            Glide.with(App.Companion.getApp()).load(product.getHeadImage()).into(iv_img);
            tv_name.setText(product.getSpecTitle());
            tv_spec.setText("规格："+product.getSpec());
            tv_color.setText("颜色："+product.getColor());
            tv_sum.setText("X"+product.getCount());
            tv_pos.setText((i+1)+"");
            tv_price.setText("¥"+product.getPrice());
            if (b){
                tv_pos.setVisibility(View.GONE);
            }else {
                tv_pos.setVisibility(View.VISIBLE);
            }
            rl_haha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isGuding) {
                        Log.d("TAG73", "------------");
                        RoomProductBean bean = new RoomProductBean();
                        bean.room = room;
                        bean.product = product;
                        MessageBus.getBus().post(new MessageBusMessage(MsgContract.COMBO_SELECTED, bean));
                    }
                }
            });
        }
    }
}
