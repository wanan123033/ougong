package com.ogmall.faceread.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.gwm.annotation.Layout;
import com.ogmalllarge.R;

import butterknife.OnClick;


/**
 * Created by root on 18-5-21.
 */

@Layout(R.layout.activity_splash_layout)
public class SplashActivity extends com.gwm.base.BaseActivity{



    @Override
    public void onCreate(FragmentManager manager, Bundle savedInstanceState) {
        checkPsermissions();

    }

    @OnClick(R.id.ll_sh)
    public void query(View view) {
        Intent intent = new Intent(getApplicationContext(),Detect2Activity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 检查权限并授权
     */
    public void checkPsermissions() {

    }

    @Override
    public void onRequestSuccessData(Object data) {

    }

    @Override
    public void onError() {

    }
}
