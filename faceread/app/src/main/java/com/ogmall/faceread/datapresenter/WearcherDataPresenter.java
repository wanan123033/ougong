package com.ogmall.faceread.datapresenter;

import android.content.Context;

import com.gwm.android.ACache;
import com.gwm.base.BaseDataPresenter;
import com.gwm.base.ViewModelCallback;
import com.gwm.retrofit.Observable;
import com.ogmall.faceread.bean.WearcherBean;
import com.ogmall.faceread.contact.HttpInterface;

import java.io.File;

/**
 * Created by Administrator on 2019/3/18.
 */

public class WearcherDataPresenter extends BaseDataPresenter {
    public WearcherDataPresenter(ViewModelCallback callback, Context context) {
        super(callback, context, HttpInterface.class);
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

    public void wearcher() {
        Observable<WearcherBean> observable = ((HttpInterface)getHttpPresenter()).getWearcher();
        addHttpSubscriber(observable,WearcherBean.class);
    }
}
