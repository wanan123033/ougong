package com.ogmallpad.presenter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.junshan.pub.widget.CommonDialog;
import com.ogmallpad.MyApp;
import com.ogmallpad.R;
import com.ogmallpad.contract.LoginContract;
import com.ogmallpad.databinding.LogoutLayoutBinding;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 * Created by chenjunshan on 2018-07-03.
 */

public class LoginPresenter extends HttpPresenter implements LoginContract.Presenter {
    private Context mContext;
    private LoginContract.View mView;
    private CommonDialog dialog;
    private LogoutLayoutBinding binding;

    public LoginPresenter(Context mContext, LoginContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}