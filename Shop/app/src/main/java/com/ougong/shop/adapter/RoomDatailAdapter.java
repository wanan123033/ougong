package com.ougong.shop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.ougong.shop.R;
import com.ougong.shop.httpmodule.ComboDatailBean;

class RoomDatailAdapter extends RecyclerView.Adapter<RoomDatailAdapter.RoomDatailViewHolder> {
    private boolean isGuding;
    private ComboDatailBean.Room room;
    private RecyclerView parent;
    private ComboDatailBean.Product[][] products;
    private Context mContext;

    public RoomDatailAdapter(RecyclerView parent, Context context, ComboDatailBean.Product[][] products, ComboDatailBean.Room room, boolean isGuding) {
        this.mContext = context;
        this.products = products;
        this.parent = parent;
        this.room = room;
        this.isGuding = isGuding;
    }

    @NonNull
    @Override
    public RoomDatailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_room_datail,viewGroup,false);
        return new RoomDatailViewHolder(view,isGuding);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomDatailViewHolder roomDatailViewHolder, int i) {
        roomDatailViewHolder.setData(products[i]);
    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    class RoomDatailViewHolder extends RecyclerView.ViewHolder{
        private boolean isGuding;
        RecyclerView xv_room_datail;
        RelativeLayout rl_bg;
        ImageView iv_selected;
        View view_yinyi;
        LinearLayout ll_img;

        public RoomDatailViewHolder(@NonNull View itemView, boolean isGuding) {
            super(itemView);
            xv_room_datail = itemView.findViewById(R.id.xv_room_datail);
            rl_bg = itemView.findViewById(R.id.rl_bg);
            iv_selected = itemView.findViewById(R.id.iv_selected);
            view_yinyi = itemView.findViewById(R.id.view_yinyi);
            ll_img = itemView.findViewById(R.id.ll_img);
            this.isGuding = isGuding;
        }

        public void setData(final ComboDatailBean.Product[] product) {
            xv_room_datail.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            xv_room_datail.setAdapter(new RoomDatailViewAdapter(xv_room_datail.getContext(),product,room,isGuding));
            xv_room_datail.setHasFixedSize(true);
            xv_room_datail.setNestedScrollingEnabled(true);

            if (product[0].getEnable()){
                rl_bg.setBackgroundResource(R.drawable.shape_combo_datail);
                view_yinyi.setVisibility(View.VISIBLE);
                iv_selected.setVisibility(View.VISIBLE);
            }else {
                rl_bg.setBackgroundResource(R.drawable.shape_combo_datail_nomal);
                view_yinyi.setVisibility(View.INVISIBLE);
                iv_selected.setVisibility(View.GONE);
            }
//            ll_img.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //TODO 选中处理
//                    Log.d("TAG73","------------");
//                    for (int i = 0 ; i < product.length ; i++){
//                        product[i].setEnable(!product[i].getEnable());
//                    }
//                    if (parent != null&& parent.getAdapter() != null){
//                        parent.getAdapter().notifyDataSetChanged();
//                    }
//                }
//            });

        }
    }
}
