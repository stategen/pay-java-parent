package com.egzosn.pay.baidu.api;

import com.egzosn.pay.common.api.PayConfigStorage;

/***
 * 
 * 
 * @author niaoge
 * @version $Id: IBaiduPayConfigStorage.java, v 0.1 2020年6月19日 上午3:06:54 XiaZhengsheng Exp $
 */
public interface IBaiduPayConfigStorage extends PayConfigStorage {

    String getAppKey();

    String getDealId();
    
}
