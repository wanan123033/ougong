package com.ougong.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.ougong.shop.R;
import com.ougong.shop.activity.orderpay.QuickOrderActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String HTML_CONTENT = "html_content";
    public static final String TITLE = "TITLE";
    public static final String PRICE = "PRICE";
    public static final String NUMBER = "NUMBER";
    public static final String BUYSTRING = "BUYSTRING";
    public static final String ORDERSTRING = "ORDERSTRING";
    public String buyStr,orderStr;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        WebView view = findViewById(R.id.web_view);
        TextView tv_priceccccc = findViewById(R.id.tv_priceccccc);
        TextView tv_commit_fa = findViewById(R.id.tv_commit_fa);

        view.getSettings().setUseWideViewPort(true);//将图片调整到适合webView的大小
        view.getSettings().setLoadWithOverviewMode(true);//缩放至屏幕大小
        view.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        view.getSettings().setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        view.getSettings().setDisplayZoomControls(false); //隐藏原生的缩放控件

        view.setWebChromeClient(new WebChromeClient());
        view.setWebViewClient(new WebViewClient());

        String html_content = getIntent().getStringExtra(HTML_CONTENT);
        String title = getIntent().getStringExtra(TITLE);
        view.loadData(html_content,"text/html","utf-8");

        TextView textView = findViewById(R.id.title_name);
        textView.setText(title);
        findViewById(R.id.title_back).setVisibility(View.VISIBLE);
        findViewById(R.id.title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        double price = getIntent().getDoubleExtra(PRICE,0.0);
        buyStr = getIntent().getStringExtra(BUYSTRING);
        orderStr = getIntent().getStringExtra(ORDERSTRING);
        int number = getIntent().getIntExtra(NUMBER,0);
        tv_priceccccc.setText("全部房间：¥"+String.format("%.2f",price));
        tv_commit_fa.setText("提交方案("+number+")");

        tv_commit_fa.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, QuickOrderActivity.class);
        try {
            orderStr = URLEncoder.encode(orderStr, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        intent.putExtra(QuickOrderActivity.ORDER_STR_CONTENT, buyStr);
        intent.putExtra(QuickOrderActivity.ORDER_TYPE,2);
        intent.putExtra(QuickOrderActivity.ORDER_DATA,orderStr);
        startActivity(intent);
    }

}
