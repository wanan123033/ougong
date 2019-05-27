package com.ougong.shop.activity.checkin;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import com.ougong.shop.R;
import com.ougong.shop.httpmodule.ProductCheckLinBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyShopSpDialog extends DialogFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private View view;
    private ProductCheckLinBean.SpecDataBean[] specifications;

    private RecyclerView gv_color,gv_spec;
    private List<ColorSpecBean> colorSpecBeans;
    private String mCurrentColor;
    private SpecBean mCurrentSpec;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_sp_shop,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        Log.e("TAG+++++","============");
        setListener();
        initData();
    }

    private void findViews() {
        gv_color = view.findViewById(R.id.gv_color);
        gv_spec = view.findViewById(R.id.gv_spec);
    }

    private void setListener() {
        view.findViewById(R.id.view_dimission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.tv_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG+++++","============"+ Arrays.toString(specifications));
                for (ProductCheckLinBean.SpecDataBean specDataBean : specifications){
                    if (specDataBean.getColor().equals(mCurrentColor) && specDataBean.getSpec().equals(mCurrentSpec.spec)){
                        specDataBean.setDefault(true);
                    }else {
                        specDataBean.setDefault(false);
                    }
                }
                Log.e("TAG+++++","============"+ Arrays.toString(specifications));
                //更新产品状态
                Activity activity = getActivity();
                if (activity instanceof CheckInFanganActivity) {
                    ((CheckInFanganActivity) activity).updateProductSpec(specifications);
                    dismiss();
                }

            }
        });
    }

    private void initData() {

        colorSpecBeans = new ArrayList<>();
        List<ColorBean> colors = new ArrayList<>();
        for (ProductCheckLinBean.SpecDataBean specDataBean : specifications){
            if (colors.contains(specDataBean.getColor())){
                continue;
            }else {
                colors.add(new ColorBean(specDataBean.getColor(),specDataBean.isDefault()));

            }
            ColorSpecBean specBean = new ColorSpecBean();
            specBean.color = specDataBean.getColor();
            specBean.specs = new ArrayList<>();
            for (ProductCheckLinBean.SpecDataBean bean : specifications){
                if (bean.getColor().equals(specBean.color)){
                    specBean.specs.add(new SpecBean(bean.getSpec(),bean.isDefault()));
                }
            }
            colorSpecBeans.add(specBean);
        }
        gv_color.setLayoutManager(new LinearLayoutManager(getContext()));
        gv_color.setAdapter(new ColorAdapter(getContext(),colorSpecBeans));
        gv_spec.setLayoutManager(new LinearLayoutManager(getContext()));
        gv_spec.setAdapter(new SpecAdapter(getContext()));

        ((SpecAdapter) gv_spec.getAdapter()).setData(colorSpecBeans.get(0).specs);
    }


    @Override
    public void onStart() {
        super.onStart();
        //设置 dialog 的宽高
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //设置 dialog 的背景为 null
        getDialog().getWindow().setBackgroundDrawable(null);
    }

    public void setData(@Nullable ProductCheckLinBean.SpecDataBean[] specifications) {
        this.specifications = specifications;
        if (isVisible()){
            initData();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.gv_color) {
            //TODO 确定了产品颜色
            Log.e("TAG","-------");
            this.mCurrentColor = colorSpecBeans.get(position).color;
            for (ColorSpecBean bean : colorSpecBeans){
                if (mCurrentColor.equals(bean.color)){
                    bean.enable = true;
                }
            }
            gv_color.getAdapter().notifyDataSetChanged();
        }else if (parent.getId() == R.id.gv_spec){
            this.mCurrentSpec = ((SpecAdapter) gv_spec.getAdapter()).getItem(position);
        }
    }

    @Override
    public void onClick(View v) {
        //TODO 提交产品配置

    }

    class ColorAdapter extends RecyclerView.Adapter<ColorViewHolder>{

        private List<ColorSpecBean> colorSpecBeans;
        private Context context;

        public ColorAdapter(Context context, List<ColorSpecBean> colorSpecBeans) {
            this.context = context;
            this.colorSpecBeans = colorSpecBeans;
        }

        @Override
        public int getItemCount() {
            return colorSpecBeans.size();
        }

        public ColorSpecBean getItem(int position) {
            return colorSpecBeans.get(position);
        }

        @NonNull
        @Override
        public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ColorViewHolder((TextView) LayoutInflater.from(context).inflate(R.layout.item_checklin_all,viewGroup,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ColorViewHolder colorViewHolder, final int i) {
            ((TextView)colorViewHolder.itemView).setText(getItem(i).color);
            if (getItem(i).enable){
                ((SpecAdapter) gv_spec.getAdapter()).setData(colorSpecBeans.get(i).specs);
                colorViewHolder.itemView.setBackgroundResource(R.drawable.img_boder);
            }else {
                colorViewHolder.itemView.setBackgroundResource(R.drawable.checklin_item_bg_nomal);
            }
            colorViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getItem(i).enable = !getItem(i).enable;
                    notifyDataSetChanged();
                    if (getItem(i).enable){
                        mCurrentColor = getItem(i).color;
                    }
                }
            });
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
    class SpecAdapter extends RecyclerView.Adapter<ColorViewHolder>{

        private Context context;
        private List<SpecBean> specs;

        public SpecAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getItemCount() {
            if (specs == null){
                return 0;
            }
            return specs.size();
        }

        public SpecBean getItem(int position) {
            if (specs == null){
                return null;
            }
            return specs.get(position);
        }

        @NonNull
        @Override
        public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ColorViewHolder((TextView) LayoutInflater.from(context).inflate(R.layout.item_checklin_all,viewGroup,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ColorViewHolder colorViewHolder, final int i) {
            ((TextView)colorViewHolder.itemView).setText(getItem(i).spec);
            if (getItem(i).enable){
                colorViewHolder.itemView.setBackgroundResource(R.drawable.img_boder);
            }else {
                colorViewHolder.itemView.setBackgroundResource(R.drawable.checklin_item_bg_nomal);
            }
            colorViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getItem(i).enable = !getItem(i).enable;
                    notifyDataSetChanged();
                    if (getItem(i).enable){
                        mCurrentSpec = getItem(i);
                    }
                }
            });
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void setData(List<SpecBean> specs) {
            this.specs = specs;
            notifyDataSetChanged();
        }
    }

    class ColorSpecBean{
        public String color;
        public List<SpecBean> specs;
        public boolean enable = false;
    }
    class SpecBean{
        public String spec;
        public boolean enable = false;

        public SpecBean(String spec, boolean aDefault) {
            this.spec = spec;
            enable = aDefault;
        }
    }
    class ColorBean{
        public String color;
        public boolean enable = false;

        public ColorBean(String color, boolean aDefault) {
            this.color = color;
            enable = aDefault;
        }
    }

    static class ColorViewHolder extends RecyclerView.ViewHolder{
        public ColorViewHolder(@NonNull TextView itemView) {
            super(itemView);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            layoutParams.topMargin = 8;
            itemView.setLayoutParams(layoutParams);
        }
    }
}
