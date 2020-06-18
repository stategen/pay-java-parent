package com.egzosn.pay.wx.api;

import static com.egzosn.pay.wx.api.WxConst.HMACSHA256;
import static com.egzosn.pay.wx.api.WxConst.HMAC_SHA256;

import com.egzosn.pay.common.api.BasePayConfigStorage;

/**
 * 微信配置存储
 * @author  egan
 *
 * <pre>
 * email egzosn@gmail.com
 * date 2016-5-18 14:09:01
 * </pre>
 */
public class WxPayConfigStorage extends BasePayConfigStorage implements IWxPayConfigStorage {


    /**
     * 微信分配的公众账号ID
     */
    private String appid ;
    /**
     * 微信分配的子商户公众账号ID
     */
    private String subAppid ;
    /**
     *  微信支付分配的商户号 合作者id
     */
    private String mchId;
    /**
     *  微信支付分配的子商户号，开发者模式下必填 合作者id
     */
    private String subMchId;
    
    /***沙箱签名 有效期3天*/
    private String sandboxSignkey;


    @Override
    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }


    /***应该在设置时判断，不能在 WxService::setPayConfigStorage中判断?? --by niaoge*/
    @Override
    public void setSignType(String signType) {
        if (HMAC_SHA256.equals(signType)) {
            signType=HMACSHA256;
        }
        super.setSignType(signType);
    }


    /**
     * 合作商唯一标识
     */
    @Override
    public String getPid() {
        return mchId;
    }

    @Override
    public String getSeller() {
        return null;
    }


    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    /**
     *  为商户平台设置的密钥key
     * @return 微信密钥
     */
    public String getSecretKey() {
        return getKeyPrivate();
    }

    public void setSecretKey(String secretKey) {
        setKeyPrivate(secretKey);
    }

    public String getSubAppid() {
        return subAppid;
    }

    public void setSubAppid(String subAppid) {
        this.subAppid = subAppid;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public void setSandboxSignkey(String sandboxSignkey) {
        this.sandboxSignkey = sandboxSignkey;
    }
    
    
    public String getSandboxSignkey() {
        return sandboxSignkey;
    }
    
}
