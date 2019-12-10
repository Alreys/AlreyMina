package com.smart.xxl.inter;

public class ReceiverListenerImpl implements ReceiveListener {

    @Override
    public boolean ReceiverData(String data) {
        return false;
    }

    @Override
    public boolean ReceiverFail(Error error) {
        return false;
    }
}
