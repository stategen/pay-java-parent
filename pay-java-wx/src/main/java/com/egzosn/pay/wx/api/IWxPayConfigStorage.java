package com.egzosn.pay.wx.api;

import com.egzosn.pay.common.api.PayConfigStorage;
/***
 * 
 * 
 * @author niaoge
 * @version $Id: IWxPayConfigStorage.java, v 0.1 2020年6月19日 上午3:19:54 XiaZhengsheng Exp $
 */
public interface IWxPayConfigStorage extends PayConfigStorage {

    String getMchId();

    String getSubMchId();

    String getSubAppid();

    String getSandboxSignkey();

    void setSandboxSignkey(String sandboxSignkey);
    
}
