package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew_debug.R;
import com.ogmamllpadnew.bean.AddressCallbackBean;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.MyshopContract;
import com.ogmamllpadnew_debug.databinding.DialogQueryLayoutBinding;
import com.ogmamllpadnew_debug.databinding.FgMyShopHeaderLayoutBinding;
import com.ogmamllpadnew_debug.databinding.FgMyshopItemLayoutBinding;
import com.ogmamllpadnew_debug.databinding.TitleRightSearchLayoutBinding;
import com.ogmamllpadnew.presenter.MyshopPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.UserselectStoreBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的店铺商
 * Created by 陈俊山 on 2018-11-12.
 */

public class MyshopFragment extends BaseFragment<FgMyshopItemLayoutBinding, UserselectStoreBean.DataBean> implements MyshopContract.View {
    private MyshopPresenter presenter;
    private int width;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_myshop_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(R.string.str_wddps);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new MyshopPresenter(getActivity(), this);
        width = ScreenUtils.getScreenWidth() / 30;
        lvBinding.llParent.setPadding(width, width / 2, width, 0);
        addHeadView();
        userselectStore();
    }

    private TitleRightSearchLayoutBinding searchLayoutBinding;
    private FgMyShopHeaderLayoutBinding mHeaderBinding;

    /**
     * 增加头部View
     */
    private void addHeadView() {
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fg_my_shop_header_layout, lvBinding.llHeader, false);
        lvBinding.llHeader.addView(mHeaderBinding.getRoot());

        //添加搜索View
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        searchLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.title_right_search_layout, lvBinding.llHeader, false);
        titleBinding.rl.addView(searchLayoutBinding.getRoot(), params);
        searchLayoutBinding.llSearch.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.USERSELECTSTORE) {
            UserselectStoreBean bean = (UserselectStoreBean) baseBean;
            addData(bean.getData());
            isLoadingMore(bean.getCount());
        } else if (mTag == HttpPresenter.APPRESETPASSWORD) {
            dialogReset.dismiss();
            ToastUtils.toast(getString(R.string.str_czmmcg));
        } else if (mTag == HttpPresenter.USERADDACCOUNTOFSTORE) {
            presenter.getDialogAddUser().dismiss();
            presenter.clearData();
            ToastUtils.toast(getString(R.string.str_tjcg));
            onRefresh();
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(FgMyshopItemLayoutBinding binding, final UserselectStoreBean.DataBean item, int position) {
        super.getListVewItem(binding, item, position);
        binding.tvBh.setText(String.valueOf(position + 1));
        binding.tvShopName.setText(item.getStoreName());
        binding.tvName.setText(item.getContactName());
        binding.tvMobile.setText(item.getContactPhone());
        binding.tvAddress.setText(item.getProvinceName() + item.getCityName() + item.getDistrictName() + item.getAddress());
        binding.llReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPasswd(item.getId());
            }
        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        userselectStore();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            userselectStore();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        SoftKeyBoardUtils.setListener(getActivity(), new SoftKeyBoardUtils.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

            }

            @Override
            public void keyBoardHide(int height) {

            }
        });
        searchLayoutBinding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showAddShop();
            }
        });
    }

    @Override
    public void toAddress(int type, String id) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TYPE, type);
        bundle.putString(Constants.ID, id);
        startActivity(AddressActivity.class, bundle);
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.ADDRESS) {
            AddressCallbackBean addressBean = (AddressCallbackBean) msgEvent.getBean();
            presenter.setAddressBean(addressBean);
        }
    }

    private void userselectStore() {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(pageNum));//第几页
        map.put("limit", String.valueOf(pageSize));//每页显示多少行
        map.put("keywords", "");//关键词搜索
        presenter.userselectStore(map);
    }

    @Override
    protected void initData() {
        super.initData();
        ImageCacheUtils.loadImage(getActivity(), MyApp.getInstance().getInfoBean().getData().getPicture(), mHeaderBinding.ivHead);
        mHeaderBinding.tvName.setText(MyApp.getInstance().getInfoBean().getData().getContactName());
        mHeaderBinding.tvMobile.setText(MyApp.getInstance().getInfoBean().getData().getContactPhone());
        mHeaderBinding.tvType.setText(MyApp.getInstance().getInfoBean().getData().getTypeName());
    }

    private CommonDialog dialogReset;
    private DialogQueryLayoutBinding queryLayoutBinding;

    private void resetPasswd(final String id) {
        if (dialogReset == null) {
            dialogReset = new CommonDialog.Builder(getActivity())
                    .setWidth(0.3f)
                    .setHeight(0.3f)
                    .setResId(R.layout.dialog_query_layout)
                    .build();
            queryLayoutBinding = (DialogQueryLayoutBinding) dialogReset.getBinding();
            queryLayoutBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogReset.dismiss();
                }
            });
            queryLayoutBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", id);
                    presenter.appresetPassword(map);
                }
            });
        }
        dialogReset.show();
    }
}