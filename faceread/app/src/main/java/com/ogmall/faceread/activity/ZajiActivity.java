package com.ogmall.faceread.activity;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.gwm.util.PermissionUtil;
import com.hwit.HwitManager;
import com.ogmalllarge.R;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;


/**
 * Created by Administrator on 2019/3/18.
 */
public class ZajiActivity extends Activity {
    private static final int OPEN=1122;
    private static final int SHUTDOWN = 5566;

    TextView tv_text;
    PermissionUtil permissionUtil;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case OPEN:
                    tv_text.append("开门...");
                    HwitManager.HwitSetIOValue(5, 1);
                    sendEmptyMessageDelayed(SHUTDOWN,5000);
                    break;
                case SHUTDOWN:
                    tv_text.append("关门...");
                    HwitManager.HwitSetIOValue(5, 0);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        tv_text = findViewById(R.id.text);
        permissionUtil = PermissionUtil.getInstance();
        checkPermission();
        initWebsocket();

    }

    private void initWebsocket() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url("ws://face.ogmall.com/app/myHandler").build();
        okHttpClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                Log.e("cmd","onOpen");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_text.setText("open...");
                    }
                });
                webSocket.send("12345");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_text.setText("onMessage...");
                    }
                });
                if (text.equals("open")){
                    handler.sendEmptyMessage(OPEN);
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_text.setText("onMessage...");
                    }
                });
                Log.e("cmd","onMessage");
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_text.setText("closeing...");
                    }
                });
                Log.e("cmd","onClosing");
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_text.setText("onClosed...");
                    }
                });
                Log.e("cmd","onClosed");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_text.setText("onFailure...");
                    }
                });
                Log.e("cmd","onFailure"+t.getMessage());
                okHttpClient.newWebSocket(request,this);
            }
        });
    }

    private void checkPermission() {
        permission(new PermissionUtil.RequestPermissionCallback() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(Object... permission) {

            }
        }, Manifest.permission.CAMERA,
                Manifest.permission.LOCATION_HARDWARE,Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_SETTINGS,Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_SETTINGS,Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.RECEIVE_BOOT_COMPLETED);
    }
    protected void permission(PermissionUtil.RequestPermissionCallback callback,String... permission){
        permissionUtil.setCallback(callback);
        permissionUtil.checkAndRequestPermissions(this,permission);
    }
}
