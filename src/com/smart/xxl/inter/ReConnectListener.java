package com.smart.xxl.inter;

/**断线重来呢监听器器*/
public interface ReConnectListener {
    void StartReconnect();
    void ReConnectSuccess();
    void ReconnectFail();
}
