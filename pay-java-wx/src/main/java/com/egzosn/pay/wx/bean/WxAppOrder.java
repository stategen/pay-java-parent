package com.egzosn.pay.wx.bean;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WxAppOrder {
    
    private String partnerid;
    
    private String appid;
    
    private String prepayid;
    
    private Long   timestamp;
    
    private String noncestr;
    
    @JSONField(name = "package")
    private String packageName;
    
}
