package com.egzosn.pay.wx.bean;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WxBaseOrder {
    private Long   timestamp;
    
    private String noncestr;   
    
    @JSONField(name = "package")
    private String packageValue;  
    
    private String sign;
}
