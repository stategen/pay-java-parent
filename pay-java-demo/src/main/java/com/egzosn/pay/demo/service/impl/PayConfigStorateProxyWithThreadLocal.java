package com.egzosn.pay.demo.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.egzosn.pay.common.api.PayConfigStorage;
import com.egzosn.pay.common.api.PayConfigStorageProxy;

public abstract class PayConfigStorateProxyWithThreadLocal<PC extends PayConfigStorage> extends PayConfigStorageProxy<PC> {
    
    final static Log logger = LogFactory.getLog(PayConfigStorateProxyWithThreadLocal.class);
    
    private static ThreadLocal<PayConfigStorage> PAY_CONFIGT_HREADLOCAL = new ThreadLocal<PayConfigStorage>();
    //    ThreadLocalUtil.regiest(PAY_CONFIGT_HREADLOCAL); 到线程池后应该清空，防止内存泄露
    
    private static ThreadLocal<String> APPID_THREADLOCAL = new ThreadLocal<String>();
    //  ThreadLocalUtil.regiest(APPID_THREADLOCAL); 到线程池后应该清空，防止内存泄露
    
    @SuppressWarnings("unchecked")
    @Override
    protected PC getStorageFormThreadLocal() {
        return (PC) PAY_CONFIGT_HREADLOCAL.get();
    }
    
    @Override
    protected void setStorageToThreadLocal(PC payConfigStorage) {
        PAY_CONFIGT_HREADLOCAL.set(payConfigStorage);
    }
    
    @Override
    protected String getAppidFromThreadLocal() {
        return APPID_THREADLOCAL.get();
    }
    
    @Override
    protected void setAppidToThreadLocal(String appid) {
        APPID_THREADLOCAL.set(appid);
    }
    
}
