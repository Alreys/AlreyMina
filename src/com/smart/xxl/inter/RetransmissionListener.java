package com.smart.xxl.inter;

/**数据重发监听器*/
public interface RetransmissionListener {
    void BedorRetransmission();//数据重发前
    void AfterRetransmission();//数据重发后
    void RetransmissionSuccess();//数据重发成功
    void RetransmissionFail();//数据重发失败
}
