package com.gwm.haware.wifi;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.util.List;

public class WifiUtil {
    private static final int WIFICIPHER_NOPASS = 0;
    private static final int WIFICIPHER_WEP = 1;
    private static final int WIFICIPHER_WPA = 2;

    WifiManager wifiManager;

    public WifiUtil(Context context) {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }



    public boolean isWifiOpen() {
        int status = wifiManager.getWifiState();
        if (status == WifiManager.WIFI_STATE_ENABLED) {
            //wifi已经打开
            return true;
        } else {
            return false;
        }
    }




    public boolean connWifi(String ssid,String password) {
        if (isWifiOpen()){
            openWifi();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int netId = wifiManager.addNetwork(createWifiConfig(ssid, password, WIFICIPHER_WPA));

        boolean enable = wifiManager.enableNetwork(netId, true);
        return enable;
    }

    public void openWifi() {
        wifiManager.setWifiEnabled(true);
    }

    public void closeWifi() {
        wifiManager.setWifiEnabled(false);
    }

    private WifiConfiguration createWifiConfig(String ssid, String password, int type) {
        //初始化WifiConfiguration
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        //指定对应的SSID
        config.SSID = "\"" + ssid + "\"";
        //如果之前有类似的配置
        WifiConfiguration tempConfig = isExist(ssid);
        if (tempConfig != null) {
            //则清除旧有配置
            wifiManager.removeNetwork(tempConfig.networkId);
        }
        //不需要密码的场景
        if (type == WIFICIPHER_NOPASS) {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            //以WEP加密的场景
        } else if (type == WIFICIPHER_WEP) {
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
            //以WPA加密的场景，自己测试时，发现热点以WPA2建立时，同样可以用这种配置连接
        } else if (type == WIFICIPHER_WPA) {
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    private WifiConfiguration isExist(String ssid) {
        List<WifiConfiguration> configs = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration config : configs) {
            if (config.SSID.equals("\""+ssid+"\"")) {
                return config;
            }
        }
        return null;
    }
}
