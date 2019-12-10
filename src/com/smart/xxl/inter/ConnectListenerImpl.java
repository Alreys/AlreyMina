package com.smart.xxl.inter;

import org.apache.mina.core.session.IoSession;

public class ConnectListenerImpl implements ConnectListener{

    @Override
    public boolean ConnectSuccess(IoSession ioSession) {
        return false;
    }

    @Override
    public boolean ConnectFail(Exception e) {
        return false;
    }

    @Override
    public boolean DisConnect(IoSession ioSession) {
        return false;
    }
}
