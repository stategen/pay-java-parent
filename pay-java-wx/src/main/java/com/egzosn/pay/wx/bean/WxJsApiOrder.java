package com.egzosn.pay.wx.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WxJsApiOrder extends WxBaseOrder {
    private String signType;
    
    private String appId;
    
 
}
