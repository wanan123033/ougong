package com.ogmamllpadnew.contract;

import com.shan.netlibrary.contract.BaseView;

/**
 * 首页
 * Created by 陈俊山 on 2018-11-02.
 */

public interface HomeContract {
    interface Model {
    }

    interface View extends BaseView {
    }

    interface Presenter {
        void queryDialog();
    }
}