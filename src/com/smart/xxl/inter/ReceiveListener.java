package com.smart.xxl.inter;

/**接收的监听器*/
public interface ReceiveListener {
    boolean ReceiverData(String data);
    boolean ReceiverFail(Error error);
}


