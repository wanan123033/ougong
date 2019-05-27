package com.ogmall.faceread.datapresenter;

import android.content.Context;

import com.gwm.android.ACache;
import com.gwm.base.BaseDataPresenter;
import com.gwm.base.ViewModelCallback;
import com.gwm.retrofit.Observable;
import com.ogmall.faceread.bean.ImageBean;
import com.ogmall.faceread.bean.RegisterBean;
import com.ogmall.faceread.contact.HttpInterface;

import java.io.File;

/**
 * Created by Administrator on 2019/3/18.
 */

public class RegisterDataPresenter extends BaseDataPresenter {
    public RegisterDataPresenter(ViewModelCallback callback, Context context) {
        super(callback, context, HttpInterface.class);
    }

    public void register(String mobile,String vode,String name,int type){
        Observable<RegisterBean> observable = ((HttpInterface)getHttpPresenter()).register(mobile,vode,name,type);
        addHttpSubscriber(observable,RegisterBean.class);
    }

    public void upload(String token,File img){
        Observable<ImageBean> observable = ((HttpInterface)getHttpPresenter()).upload(img,token);
        addHttpSubscriber(observable,ImageBean.class);
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
