package com.egzosn.pay.union.api;

import java.io.IOException;
import java.io.InputStream;

import com.egzosn.pay.common.api.PayConfigStorage;

/***
 * 
 * 
 * @author niaoge
 * @version $Id: IUnionPayConfigStorage.java, v 0.1 2020年6月19日 上午3:15:37 XiaZhengsheng Exp $
 */
public interface IUnionPayConfigStorage extends PayConfigStorage {
    
    String getVersion();
    
    String getAccessType();
    
    boolean isCertSign();
    
    InputStream getKeyPrivateCertInputStream() throws IOException;
    
    InputStream getAcpMiddleCertInputStream() throws IOException;
    
    InputStream getAcpRootCertInputStream() throws IOException;
    
}
