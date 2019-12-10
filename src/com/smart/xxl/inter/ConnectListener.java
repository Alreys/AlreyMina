package com.smart.xxl.inter;

import org.apache.mina.core.session.IoSession;

/**连接监听器*/
public interface ConnectListener {
    boolean ConnectSuccess(IoSession ioSession);
    boolean ConnectFail(Exception e);
    boolean DisConnect(IoSession ioSession);
}
