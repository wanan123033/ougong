package com.ogmall.faceread.datapresenter;

import android.content.Context;

import com.gwm.android.ACache;
import com.gwm.base.BaseDataPresenter;
import com.gwm.base.ViewModelCallback;
import com.gwm.retrofit.Observable;
import com.ogmall.faceread.bean.CodeBean;
import com.ogmall.faceread.contact.HttpInterface;

import java.io.File;

/**
 * Created by Administrator on 2019/3/18.
 */

/**
 * 获取验证码
 */
public class GetCodeDataPresenter extends BaseDataPresenter {
    public GetCodeDataPresenter(ViewModelCallback callback, Context context) {
        super(callback, context, HttpInterface.class);
    }

    public void getCode(String mobile){
        Observable<CodeBean> observable = ((HttpInterface)getHttpPresenter()).getCode(mobile);
        addHttpSubscriber(observable,CodeBean.class);
    }

    @Override
    protected void onNextFileResult(ACache cache, File response, int id) {

    }

    @Override
    protected void onNextResult(ACache cache, Object response, int id) {

    }

    @Override
    protected void onErrorResult(Exception e, int id) {

    }
}
