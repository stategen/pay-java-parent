package com.egzosn.pay.common.api;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/***
 * PayConfigStorage的动态代理，支持多appid，请求中的request或head 或cookie中应该包含appid
 * 
 * @author niaoge
 * @version $Id: PayConfigStorageProxy.java, v 0.1 2020年6月18日 下午5:52:21 XiaZhengsheng Exp $
 */
public abstract class PayConfigStorageProxy<PC extends PayConfigStorage> implements MethodInterceptor {
    
    final static Log logger = LogFactory.getLog(PayConfigStorageProxy.class);
    
    /*** 公平锁,应该保证只有一个线程创建PayConfigStorage成功，其它线程都从缓存中获得 */
    private static ReentrantLock LOCK = new ReentrantLock(true);
    
    /**** 弱引用缓存多WeChatPayConfig */
    private static ConcurrentHashMap<String, PayConfigStorage> PAY_CONFIG_CACHES = new ConcurrentHashMap<String, PayConfigStorage>(5);
    
    /***
     * 从获取ThreadLocal获取PayConfigStorage,为了防止内存泄露，需要用户代码实现ThreadLocal，线程休眠时自己释放数据， 或者用netty的FastThreadLocal自动释放数据,
     * 因为前期获得的Storage可能是个半成品，比如要利用已有数据从微信服务器上再获取签名，因为不能立即放入PAY_CONFIG_CACHES让其它线程拿到
     */
    public abstract PC getStorageFormThreadLocal();
    
    /***
     * 把 PayConfigStorage 放置到ThreadLocal中，@see PayConfigStorageProxy::getStorageFormThreadLocal
     */
    public abstract void setStorageToThreadLocal(PC payConfigStorage);
    
    /*** 获取放置appid的ThreadLocal,为了防止内存泄露，需要用户代码实现，线程休眠时自己释放，或者用netty的FastThreadLocal ,appid在每个应用，不同提供商下都不是相同的，放在线程里不冲突 */
    public abstract String getAppidFromThreadLocal();
    
    /*** 把appid放置到ThreadLocal中,为了防止内存泄露，需要用户代码实现，线程休眠时自己释放，或者用netty的FastThreadLocal,appid在每个应用，不同提供商下都不是相同的，放在线程里不冲突 */
    public abstract void setAppidToThreadLocal(String appid);
    
    /*** 填充PayConfigStorage的各种属性,可以从数据库或者properties中拿到，包装 httpConfigStorage */
    public abstract void assignPayConfigStorage(String appid, PC payConfigStorage);
    
    /*** 获取当前 PayConfigStorage的类型,Class */
    public abstract Class<? extends PC> getPayConfigStorageClz();
    
    /**
     * 获取被代理接口实例对象
     */
    @SuppressWarnings("unchecked")
    public PC createProxy() {
        Class<? extends PC> payConfigStorageClz = getPayConfigStorageClz();
        Enhancer            enhancer            = new Enhancer();
        //把父类设置为谁？
        //这一步就是告诉cglib，生成的子类需要继承哪个类
        enhancer.setSuperclass(payConfigStorageClz);
        //设置回调
        enhancer.setCallback(this);
        
        //第一步、生成源代码
        //第二步、编译成class文件
        //第三步、加载到JVM中，并返回被代理对象
        return (PC) enhancer.create();
    }
    
    /**
     * sub：cglib生成的代理对象 method：被代理对象方法 objects：方法入参 methodProxy: 代理方法
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        PayConfigStorage payConfigStorage = getPayConfigStorage(true);
        Object           result           = method.invoke(payConfigStorage, args);
        return result;
    }
    
    @SuppressWarnings("unchecked")
    protected PC getPayConfigStorage(boolean createIfNull) throws InstantiationException, IllegalAccessException {
        //第1次都到缓存里拿
        //先从ThreadLocal中拿，因为，如果是测试环境，还有会有额外设置（获取沙箱签名），是个半成品，所以暂时不能放到放到 weChatPayConfigCaches中
        PC payConfigStorage = getStorageFormThreadLocal();
        if (payConfigStorage != null) {
            return payConfigStorage;
        }
        
        String appid = getAppidFromThreadLocal();
        if (appid != null) {
            payConfigStorage = (PC) PAY_CONFIG_CACHES.get(appid);
            if (payConfigStorage != null) {
                //直接放到ThreadLocal中
                setStorageToThreadLocal(payConfigStorage);
                return payConfigStorage;
            }
        }
        
        if (!createIfNull) {
            return null;
        }
        
        //如果没拿到，获取锁，这个只有在初次创建时，才会执行到
        LOCK.lock();
        try {
            //lock之后，第一个获得锁的线程执行完毕，缓存中应该有值,因些，其它线程应该判断缓存里是否有值，则不应该再计算一次
            payConfigStorage = getPayConfigStorage(false);
            if (payConfigStorage != null) {
                return payConfigStorage;
            }
            return createAndPutPayConfigStorageToCache(appid);
        } finally {
            LOCK.unlock();
        }
    }
    
    protected PC createAndPutPayConfigStorageToCache(String appid) throws InstantiationException, IllegalAccessException {
        
        PC payConfigStorage = getPayConfigStorageClz().newInstance();
        //创建之后，立即放置到threadLocal中
        setStorageToThreadLocal(payConfigStorage);
        
        assignPayConfigStorage(appid, payConfigStorage);
        
        //就绪后，放到缓存里，以供其它线程使用
        PAY_CONFIG_CACHES.put(appid, payConfigStorage);
        return payConfigStorage;
    }
    
}
