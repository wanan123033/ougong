package com.ougong.shop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ougong.shop.R;
import com.ougong.shop.httpmodule.ComboDatailBean;

import java.util.List;

public class ComboDatailAdapter extends RecyclerView.Adapter<ComboDatailAdapter.ComboDatailViewHolder> {
    private boolean isGuding;
    private List<ComboDatailBean.Room> mdata;
    private Context mContext;

    public ComboDatailAdapter(Context context, List<ComboDatailBean.Room> data, boolean isGuding){
        this.mContext = context;
        this.mdata = data;
        this.isGuding = isGuding;
    }
    @NonNull
    @Override
    public ComboDatailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_combo_datail,viewGroup,false);
        return new ComboDatailViewHolder(this,view,isGuding);
    }

    @Override
    public void onBindViewHolder(@NonNull ComboDatailViewHolder comboDatailViewHolder, int i) {
        comboDatailViewHolder.setData(mdata.get(i));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    static class ComboDatailViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView.Adapter adapter;
        private boolean isGuding;
        TextView tv_roomType;
        TextView tv_yixuan;
        TextView tv_price;
        RecyclerView xv_room;
        RelativeLayout rl_dd;
        ImageView iv_xia;
        public ComboDatailViewHolder(ComboDatailAdapter comboDatailAdapter, @NonNull View itemView, boolean isGuding) {
            super(itemView);
            tv_roomType = itemView.findViewById(R.id.tv_roomType);
            tv_yixuan = itemView.findViewById(R.id.tv_yixuan);
            tv_price = itemView.findViewById(R.id.tv_price);
            xv_room = itemView.findViewById(R.id.xv_room);
            rl_dd = itemView.findViewById(R.id.rl_dd);
            iv_xia = itemView.findViewById(R.id.iv_xia);
            this.isGuding = isGuding;
            this.adapter = comboDatailAdapter;
        }

        public void setData(final ComboDatailBean.Room room) {
            tv_roomType.setText(room.getRoomName());
            tv_price.setText(price(room.getRoomPrice()));
            xv_room.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            xv_room.setAdapter(new RoomDatailAdapter(xv_room,itemView.getContext(),room.getProducts(),room,isGuding));
            xv_room.setHasFixedSize(true);
            xv_room.setNestedScrollingEnabled(true);
            tv_yixuan.setText(String.valueOf(room.getRoomNum()));
            if (room.isShow()){
                xv_room.setVisibility(View.VISIBLE);
                iv_xia.setImageResource(R.drawable.img_shang);
            }else {
                xv_room.setVisibility(View.GONE);
                iv_xia.setImageResource(R.drawable.img_xia);
            }

            rl_dd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    room.setShow(!room.isShow());
                    if (adapter != null)
                        adapter.notifyDataSetChanged();
                }
            });
        }

        private String price(double products) {
            return String.format("%.2f",products);
//            double price = 0.0;
//            for (int i = 0 ; i < products.length ; i++){
//                for (int j = 0 ; j < products[i].length ; j++){
//                    price += products[i][j].getPrice();
//                }
//            }
//            return  String.format("%.2f", price);
        }
    }
}
