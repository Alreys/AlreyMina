package com.smart.xxl.config;

import com.smart.xxl.inter.*;
import com.smart.xxl.mina.MinaClientHandler;
import com.smart.xxl.mina.MinaKeepAliveFilter;
import com.smart.xxl.mina.MinaKeepAliveRequestTimeoutHandler;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutException;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import sun.net.www.http.KeepAliveCache;
import sun.net.www.http.KeepAliveStream;

public class Config {
    private String ip;
    private int port;
    private boolean iskeepalive;
    private boolean isreconnect;
    private boolean isresend;

    private ConnectListener connectListener;
    private ConnectListenerImpl connectListenerimpl = new ConnectListenerImpl();
    private KeepAliveListener keepAliveListener;
    private KeepAliveListener keepAliveListenerimpl = new KeepAliveListenerImpl();
    private ReceiveListener receiveListener;
    private ReceiverListenerImpl receiverListenerimpl = new ReceiverListenerImpl();
    private SendListener sendListener;
    private SendListenerImpl sendListenerimpl = new SendListenerImpl();
    private ReConnectListener reConnectListener;
    private ReConnectListenerImpl reConnectListenerimpl = new ReConnectListenerImpl();
    private RetransmissionListener retransmissionListener;
    private RetransmissionListenerImpl retransmissionListenerimpl = new RetransmissionListenerImpl();

    /**长链接心跳机制配置*/
    private String heartRequest = "0x11";
    private String heartResponse = "0x12";
    private int heartFrequency = 10;//心跳频率
    private KeepAliveFilter keepAliveFilter;//心跳过滤器
    private KeepAliveRequestTimeoutHandler keepAliveRequestTimeoutHandler;
    private int hearttimeout = 20;
    private boolean isforwardevent = false;
    private IdleStatus idleStatus = IdleStatus.READER_IDLE;
    private KeepAliveRequestTimeoutException keepAliveRequestTimeoutException;//请求超时异常逻辑处理
    private KeepAliveCache keepAliveCache;
    private KeepAliveStream keepAliveStream;
    private KeepAliveMessageFactory keepAliveMessageFactory;
    private IoHandler ClientHandler;
    private int ReadbufferSize = 10240;

    /**断线重连机制*/
    private long reconnectFrequency = 5000;//重连频率
    private long reconnectNumber;//重连次数

    private int MAX_VALUE = 10;//重发队列上限

    private Config() {
    }

    private static volatile Config instance = null;

    //单例模式
    public static Config newInstance() {
        synchronized (Config.class) {
            if (instance == null) {
                instance = new Config();
            }
        }

        return instance;
    }

    public ConnectListener getConnectListener() {
        return connectListener == null ? connectListenerimpl : connectListener;
    }

    public void setConnectListener(ConnectListener connectListener) {
        this.connectListener = connectListener;
    }

    public KeepAliveListener getKeepAliveListener() {
        return keepAliveListener == null ? keepAliveListenerimpl : keepAliveListener;
    }

    public void setKeepAliveListener(KeepAliveListener keepAliveListener) {
        this.keepAliveListener = keepAliveListener;
    }

    public ReceiveListener getReceiveListener() {
        return receiveListener == null ? receiverListenerimpl : receiveListener;
    }

    public void setReceiveListener(ReceiveListener receiveListener) {
        this.receiveListener = receiveListener;
    }

    public SendListener getSendListener() {

        return sendListener == null ? sendListenerimpl:sendListener;
    }

    public void setSendListener(SendListener sendListener) {
        this.sendListener = sendListener;
    }

    public ReConnectListener getReConnectListener() {
        return reConnectListener == null ? reConnectListenerimpl : reConnectListener;
    }

    public void setReConnectListener(ReConnectListener reConnectListener) {
        this.reConnectListener = reConnectListener;
    }

    public RetransmissionListener getRetransmissionListener() {
        return retransmissionListener == null ? retransmissionListenerimpl : retransmissionListener;
    }

    public void setRetransmissionListener(RetransmissionListener retransmissionListener) {
        this.retransmissionListener = retransmissionListener;
    }

    public int getHearttimeout() {
        return hearttimeout;
    }

    public int getReadbufferSize() {
        return ReadbufferSize;
    }

    public void setReadbufferSize(int readbufferSize) {
        ReadbufferSize = readbufferSize;
    }

    public IoHandler getClientHandler() {
        return ClientHandler;
    }

    public void setClientHandler(IoHandler clientHandler) {
        ClientHandler = clientHandler;
    }

    public KeepAliveMessageFactory getKeepAliveMessageFactory() {
        return keepAliveMessageFactory;
    }

    public void setKeepAliveMessageFactory(KeepAliveMessageFactory keepAliveMessageFactory) {
        this.keepAliveMessageFactory = keepAliveMessageFactory;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean iskeepalive() {
        return iskeepalive;
    }

    public void setkeepalive(boolean iskeepalive) {
        this.iskeepalive = iskeepalive;
    }

    public boolean isreconnect() {
        return isreconnect;
    }

    public void setreconnect(boolean isreconnect) {
        this.isreconnect = isreconnect;
    }

    public boolean isresend() {
        return isresend;
    }

    public void setresend(boolean isresend) {
        this.isresend = isresend;
    }

    public String getHeartRequest() {
        return heartRequest;
    }

    public void setHeartRequest(String heartRequest) {
        this.heartRequest = heartRequest;
    }

    public String getHeartResponse() {
        return heartResponse;
    }

    public void setHeartResponse(String heartResponse) {
        this.heartResponse = heartResponse;
    }

    public int getHeartFrequency() {
        return heartFrequency;
    }

    public void setHeartFrequency(int heartFrequency) {
        this.heartFrequency = heartFrequency;
    }

    public KeepAliveFilter getKeepAliveFilter() {
        return keepAliveFilter;
    }

    public void setKeepAliveFilter(KeepAliveFilter keepAliveFilter) {
        this.keepAliveFilter = keepAliveFilter;
    }

    public KeepAliveRequestTimeoutHandler getKeepAliveRequestTimeoutHandler() {
        return keepAliveRequestTimeoutHandler;
    }

    public void setKeepAliveRequestTimeoutHandler(KeepAliveRequestTimeoutHandler keepAliveRequestTimeoutHandler) {
        this.keepAliveRequestTimeoutHandler = keepAliveRequestTimeoutHandler;
    }

    public int getHeartTimeOut() {
        return hearttimeout;
    }

    public void setHearttimeout(int hearttimeout) {
        this.hearttimeout = hearttimeout;
    }

    public boolean IsForwardEvent() {
        return isforwardevent;
    }

    public void setIsforwardevent(boolean isforwardevent) {
        this.isforwardevent = isforwardevent;
    }

    public IdleStatus getIdleStatus() {
        return idleStatus;
    }

    public void setIdleStatus(IdleStatus idleStatus) {
        this.idleStatus = idleStatus;
    }

    public KeepAliveRequestTimeoutException getKeepAliveRequestTimeoutException() {
        return keepAliveRequestTimeoutException;
    }

    public void setKeepAliveRequestTimeoutException(KeepAliveRequestTimeoutException keepAliveRequestTimeoutException) {
        this.keepAliveRequestTimeoutException = keepAliveRequestTimeoutException;
    }

    public KeepAliveCache getKeepAliveCache() {
        return keepAliveCache;
    }

    public void setKeepAliveCache(KeepAliveCache keepAliveCache) {
        this.keepAliveCache = keepAliveCache;
    }

    public KeepAliveStream getKeepAliveStream() {
        return keepAliveStream;
    }

    public void setKeepAliveStream(KeepAliveStream keepAliveStream) {
        this.keepAliveStream = keepAliveStream;
    }

    public long getReconnectFrequency() {
        return reconnectFrequency;
    }

    public void setReconnectFrequency(long reconnectFrequency) {
        this.reconnectFrequency = reconnectFrequency;
    }

    public long getReconnectNumber() {
        return reconnectNumber;
    }

    public void setReconnectNumber(long reconnectNumber) {
        this.reconnectNumber = reconnectNumber;
    }

    public boolean isIsresend() {
        return isresend;
    }

    public void setIsresend(boolean isresend) {
        this.isresend = isresend;
    }

    public int getMAX_VALUE() {
        return MAX_VALUE;
    }

    public void setMAX_VALUE(int MAX_VALUE) {
        this.MAX_VALUE = MAX_VALUE;
    }
}
