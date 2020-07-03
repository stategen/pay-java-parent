package com.egzosn.pay.ali.api;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AliPayOrder {
    
    @JSONField(name="app_id")
    private String appId;
    
    private String method;
    
    private String charset;
    
    private String timestamp;
    
    private String version;
    
    @JSONField(name="notify_url")
    private String notifyUrl;
    
    
    private String format;
    
    @JSONField(name="app_auth_token")
    private String appAuthToken;
    
    @JSONField(name="biz_content")
    private AliPayBizContent bizContent;
    

    
    
    
    
}
