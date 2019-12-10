package com.smart.xxl.inter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;

public class KeepAliveListenerImpl implements KeepAliveListener {
    @Override
    public void BeforeSendHeartbeat(IoSession arg0) {

    }

    @Override
    public void AfterReceiverHeartbeat(IoSession arg0, Object arg1) {

    }

    @Override
    public void HeartbeatTimeOut(KeepAliveFilter arg0, IoSession arg1) {

    }
}
