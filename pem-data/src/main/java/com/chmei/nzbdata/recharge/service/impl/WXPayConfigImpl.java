package com.chmei.nzbdata.recharge.service.impl;

import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @Author fuchangshuai
 * @date 2019/3/13 5:29 PM
 */
@Service
public class WXPayConfigImpl implements WXPayConfig {
    @Value("${wx.app.appId}")
    String appID;

    @Value("${wx.app.key}")
    String key;

    @Value("${wx.app.mchId}")
    String mchID;

    @Override
    public String getAppID()
    {
        return appID;
    }

    @Override
    public String getMchID()
    {
        return mchID;
    }

    @Override
    public String getKey()
    {
        return key;
    }

    @Override
    public InputStream getCertStream()
    {
        return this.getClass().getResourceAsStream("/apiclient_cert.p12");
    }

    @Override
    public int getHttpConnectTimeoutMs()
    {
        return 2000;
    }

    @Override
    public int getHttpReadTimeoutMs()
    {
        return 10000;
    }
}