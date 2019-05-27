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
import com.ougong.shop.httpmodule.RecommendBean;
import com.ougong.shop.utils.MessageBus;
import com.ougong.shop.utils.MessageBusMessage;

import java.util.List;

public class ComboRammodAdapter extends RecyclerView.Adapter<ComboRammodAdapter.ViewHolder>{
    private List<RecommendBean.DataBean> recommendData;
    private Context context;

    public ComboRammodAdapter(Context context, List<RecommendBean.DataBean> recommendData){
        this.context = context;
        this.recommendData = recommendData;
    }

    public ViewHolder onCreateViewHolder(ViewGroup p0, int p1){
        View view = LayoutInflater.from(context).inflate(R.layout.item_room_datail_item,p0,false);
        return new ViewHolder(view);
    }

    public int getItemCount() {
        Log.d("TTTTT","ddddddddddddddd------"+recommendData);
        if (recommendData == null){
            return 0;
        }
        return recommendData.size();
    }

    public void onBindViewHolder( ViewHolder p0, int p1) {
        RecommendBean.DataBean data = recommendData.get(p1);
        p0.setData(data,p1);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_img;
        private TextView tv_name,tv_spec,tv_color,tv_sum,tv_pos,tv_price;
        private RelativeLayout rl_haha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_spec = itemView.findViewById(R.id.tv_spec);
            tv_color = itemView.findViewById(R.id.tv_color);
            tv_sum = itemView.findViewById(R.id.tv_sum);
            tv_pos = itemView.findViewById(R.id.tv_pos);
            tv_price = itemView.findViewById(R.id.tv_price);
            rl_haha = itemView.findViewById(R.id.rl_haha);
        }

        public void setData(final RecommendBean.DataBean data, int i) {
            Glide.with(App.Companion.getApp()).load(data.getProduct().getHeadImage()).into(iv_img);
            tv_name.setText(data.getTitle());
            tv_spec.setText("规格："+data.getSpec());
            tv_color.setText("颜色:"+data.getColor());
            tv_sum.setText("X"+data.getMinCount());
            tv_pos.setVisibility(View.GONE);
            tv_price.setText("¥"+data.getPrice());

            if (data.getProduct().getEnable()){
                rl_haha.setBackgroundResource(R.drawable.shape_combo_datail);
            }else {
                rl_haha.setBackground(null);
            }
            rl_haha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageBus.getBus().post(new MessageBusMessage(MsgContract.COMBO_SELECTED_RECOMMED,data.getProduct()));
                }
            });
        }
    }
}