package com.egzosn.pay.common.api;

import java.util.Map;

import com.egzosn.pay.common.bean.PayMessage;
import com.egzosn.pay.common.bean.PayOutMessage;
import com.egzosn.pay.common.exception.PayErrorException;


/**
 * 处理支付回调消息的处理器接口
 *
 * @author egan
 * <pre>
 *     email egzosn@gmail.com
 *     date 2016-6-1 11:40:30
 *
 *
 *     source Daniel Qian
 *  </pre>
 */
public interface PayMessageHandler<M extends PayMessage, S extends PayService> {

    /**
     * 处理支付回调消息的处理器接口
     * @param payMessage 支付消息
     * @param context        上下文，如果handler或interceptor之间有信息要传递，可以用这个
     * @param payService 支付服务
     * @return xml,text格式的消息，如果在异步规则里处理的话，可以返回null
     * @throws PayErrorException 支付错误异常
     */
    PayOutMessage handle(M payMessage,
                                Map<String, Object> context,
                         S payService
    ) throws PayErrorException;
    
    /***
     * 多appid,在验瓬之前，需要通过appid获取payConfigStorage
     * 
     * @param payMessage
     * @param payService
     */
    default void preSignVerify(M payMessage, S payService) {
        
    }
}
