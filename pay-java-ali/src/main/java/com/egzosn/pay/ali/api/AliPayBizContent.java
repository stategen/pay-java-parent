package com.egzosn.pay.ali.api;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AliPayBizContent {
    
    private String body;
    
    @JSONField(name = "seller_id")
    private String sellerId;
    
    private String subject;
    
    @JSONField(name="out_trade_no")
    private String outTradeNo;
    
    @JSONField(name="total_amount")
    private String totalAmount;
    
    @JSONField(name="passback_params")
    private String passbackParams;
    
    @JSONField(name="product_code")
    private String productCode;
    
    @JSONField(name="return_url")
    private String returnUrl;
    
    @JSONField(name="extend_params")
    private String extendParams;
    
    @JSONField(name="buyer_id")
    private String buyerId;
    
    private String scene;
    
    @JSONField(name="auth_code")
    private String authCode;
    
    @JSONField(name="qr_code_timeout_express")
    private String qrCodeTimeoutExpress;
    
    @JSONField(name="timeout_express")
    private String timeoutExpress;
}
