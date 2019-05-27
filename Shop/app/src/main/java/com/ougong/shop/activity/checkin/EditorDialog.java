package com.ougong.shop.activity.checkin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.baigui.commonlib.utils.ToastUtils;
import com.ougong.shop.R;
import com.ougong.shop.activity.CheckLinData;
import com.ougong.shop.httpmodule.HxSpaceBean;

import java.util.ArrayList;
import java.util.List;

public class EditorDialog extends DialogFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private View view;
    private EditText ed_bj;
    private GridView gv_bj;
    private List<HxSpaceBean> beans;
    private List<HxSpaceBean> mCurrentbeans = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.dialog_editor,container,false);
    }

    public void onStart() {
        super.onStart();
        //设置 dialog 的宽高
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置 dialog 的背景为 null
        getDialog().getWindow().setBackgroundDrawable(null);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        setListener();
        initData();
    }

    private void initData() {
        beans = CheckLinUtils.getInstance().getCheckHxNames();
        mCurrentbeans.clear();
        for (HxSpaceBean bean:beans){
            mCurrentbeans.add(bean);
        }
        gv_bj.setAdapter(new HxAdapter(getContext(),mCurrentbeans));
    }

    private void setListener() {
        view.findViewById(R.id.iv_close).setOnClickListener(this);
        view.findViewById(R.id.tv_ok).setOnClickListener(this);
        view.findViewById(R.id.tv_reset).setOnClickListener(this);
        view.findViewById(R.id.tv_commit).setOnClickListener(this);
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
        view.findViewById(R.id.tv_ok).setOnClickListener(this);

        gv_bj.setOnItemClickListener(this);


    }

    private void findViews() {
        ed_bj = view.findViewById(R.id.ed_bj);
        gv_bj = view.findViewById(R.id.gv_bj);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_commit:  //确定
                CheckLinUtils.getInstance().initRoomCateAndProduct(mCurrentbeans);
                //TODO 通知CheckInFanganActivity 显示新增的房间
                if (getActivity() instanceof CheckInFanganActivity){
                    ((CheckInFanganActivity)getActivity()).initTabLayout(CheckLinUtils.getInstance().getCheckHxNames());
                }
                dismiss();
                break;
            case R.id.tv_reset:  //重置
                dismiss();
                break;
            case R.id.tv_ok: //添加房间名称
                String name = ed_bj.getText().toString().trim();
                if (TextUtils.isEmpty(name)){
                    ToastUtils.toast(getContext(),"请输入房间名称");
                    break;
                }
                HxSpaceBean bean = new HxSpaceBean();
                bean.setName(name);
                mCurrentbeans.add(bean);
                ((HxAdapter)gv_bj.getAdapter()).notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    static class HxAdapter extends BaseAdapter{

        private final Context context;
        private final List<HxSpaceBean> checkHxNames;

        public HxAdapter(Context context, List<HxSpaceBean> checkHxNames) {
            this.context = context;
            this.checkHxNames = checkHxNames;
        }

        @Override
        public int getCount() {
            return checkHxNames.size();
        }

        @Override
        public HxSpaceBean getItem(int position) {
            return checkHxNames.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.item_checklin_all,parent,false);
            }
            ((TextView)convertView).setText(getItem(position).getName());
            return convertView;
        }
    }
}
