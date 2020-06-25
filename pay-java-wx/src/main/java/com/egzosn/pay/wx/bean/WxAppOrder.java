package com.egzosn.pay.wx.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WxAppOrder  extends WxBaseOrder{
    
    private String partnerid;
    
    private String appid;
    
    private String prepayid;
    
}
