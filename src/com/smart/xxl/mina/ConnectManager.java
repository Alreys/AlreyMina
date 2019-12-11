package com.smart.xxl.mina;

import com.smart.xxl.config.Config;
import com.smart.xxl.utils.ByteArrayCodecFactory;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import java.net.InetSocketAddress;

public class ConnectManager {
    private Config config;
    private boolean isconnect = false;// 是否连接成功
    private NioSocketConnector nioSocketConnector = null;
    private ConnectFuture connectFuture = null;
    private KeepAliveFilter keepAliveFilter = null;
    public volatile IoSession ioSession;
    private Thread resend;
    public boolean closemark = false;//true表示主动断开链接

    private ConnectManager() {
    }

    public void creatConfig(Config config) {
        this.config = config;
    }

    private static volatile ConnectManager instance = null;

    //单例模式
    public static ConnectManager newInstance() {
        if (instance == null) {
            synchronized (ConnectManager.class) {
                if (instance == null) {
                    instance = new ConnectManager();
                }
            }
        }
        return instance;
    }


    /**
     * 连接初始化
     */
    private boolean initConnect() {
        if (nioSocketConnector == null) {
            nioSocketConnector = new NioSocketConnector();
        } else {
            nioSocketConnector.dispose();// 关闭原有的连接器
            nioSocketConnector = new NioSocketConnector();
        }

        if (config.iskeepalive()) {
            if (config.getKeepAliveMessageFactory() != null)
                keepAliveFilter = new KeepAliveFilter(config.getKeepAliveMessageFactory(), config.getIdleStatus(), KeepAliveRequestTimeoutHandler.CLOSE);
            else
                keepAliveFilter = new KeepAliveFilter(new MinaKeepAliveFilter(config), config.getIdleStatus(), KeepAliveRequestTimeoutHandler.CLOSE);
            keepAliveFilter.setForwardEvent(config.IsForwardEvent());// 继续调用 IoHandlerAdapter 中的 sessionIdle时间
            keepAliveFilter.setRequestInterval(config.getHeartFrequency());// 设置心跳发送时间间隔/s
            keepAliveFilter.setRequestTimeout(config.getHeartTimeOut());// 设置心跳判断的超时时间/s
            if (config.getKeepAliveRequestTimeoutHandler() != null)
                keepAliveFilter.setRequestTimeoutHandler(config.getKeepAliveRequestTimeoutHandler());// 设置超时的逻辑处理类
            else
                keepAliveFilter.setRequestTimeoutHandler(new MinaKeepAliveRequestTimeoutHandler(config));// 设置超时的逻辑处理类
            nioSocketConnector.getFilterChain().addLast("keepalive", keepAliveFilter);
        }

        try {
            isconnect = false;
            nioSocketConnector.setConnectTimeoutMillis(1000);// 设置连接超时时间/ms
            nioSocketConnector.getSessionConfig().setReadBufferSize(config.getReadbufferSize());// 设置接收缓冲区大小/b
            nioSocketConnector.getFilterChain().addFirst("logger", new LoggingFilter());
            nioSocketConnector.getFilterChain().addFirst("codec", new ProtocolCodecFilter(new ByteArrayCodecFactory()));//添加字符过滤器
            if (config.getClientHandler() != null)
                nioSocketConnector.setHandler(config.getClientHandler());// 消息处理
            else
                nioSocketConnector.setHandler(new MinaClientHandler(config));
            connectFuture = nioSocketConnector.connect(new InetSocketAddress(config.getIp(), config.getPort()));// 创建连接
            connectFuture.awaitUninterruptibly();// 等待连接
            ioSession = connectFuture.getSession();
            if (resend == null){
                resend = new Thread(SendManager.newInstance());//开启数据重发线程
                resend.start();
            }
            isconnect = true;
            if (isconnect && config.getConnectListener() != null && ioSession != null) {
                config.getConnectListener().ConnectSuccess(ioSession);
            }
        } catch (Exception e) {
            isconnect = false;
            ioSession = null;
            e.printStackTrace();
            if (config.getConnectListener() != null) {
                config.getConnectListener().ConnectFail(e);
            }
        }
        return isconnect;
    }

    /**
     * 关闭链接
     * */
    public void close(){
        if (isconnect){
            ioSession.closeNow();
            ioSession = null;
            isconnect = false;
            closemark = true;
            resend.stop();
            resend = null;
        }
    }

    /**
     * 连接
     */
    public boolean connect() {
        isconnect = false;
        closemark = false;
        while (!isconnect && !closemark) {
            try {
                System.out.println("开始连接...");
                initConnect();
                Thread.sleep(config.getReconnectFrequency());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 发送数据
     */
    public boolean sendmessage(String data) {
        SendManager.newInstance().send(data);
        return true;
    }

}
