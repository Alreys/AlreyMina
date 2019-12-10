package com.smart.xxl.inter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;

/**长链接的监听器*/
public interface KeepAliveListener {
    void BeforeSendHeartbeat(IoSession arg0);//发送心跳前
    void AfterReceiverHeartbeat(IoSession arg0, Object arg1);//接收心跳后
    void HeartbeatTimeOut(KeepAliveFilter arg0, IoSession arg1);
}
