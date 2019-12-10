package com.smart.xxl.mina;

import com.smart.xxl.config.Config;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

public class MinaKeepAliveFilter implements KeepAliveMessageFactory {
    private IoBuffer iobuffermessage;
    private byte[] b;
    private Config config;
    private String int_req;
    private String int_rep;
    private IoBuffer KAMSG_REQ;
    private IoBuffer KAMSG_REP;

    public MinaKeepAliveFilter(Config config){
        this.config = config;
        int_req = config.getHeartRequest();
        int_rep = config.getHeartResponse();
        KAMSG_REQ = IoBuffer.wrap(int_req.getBytes());
        KAMSG_REP = IoBuffer.wrap(int_rep.getBytes());
    }

    /**
     * 此方法为发心跳包，发0x11
     */
    @Override
    public Object getRequest(IoSession arg0) {
        if (config.getKeepAliveListener() != null)
        config.getKeepAliveListener().BeforeSendHeartbeat(arg0);
        return KAMSG_REQ.duplicate();
    }

    /**
     * 收到对方的心跳包后，此方法回心跳包，回0x12
     */
    @Override
    public Object getResponse(IoSession arg0, Object arg1) {
        if (config.getKeepAliveListener() != null)
            config.getKeepAliveListener().AfterReceiverHeartbeat(arg0, arg1);
        return KAMSG_REP.duplicate();
    }

    /**
     * 是否是心跳请求包，不管是对方的还是自己的
     */
    @Override
    public boolean isRequest(IoSession session, Object message) {
        if (message instanceof IoBuffer) {
            iobuffermessage = (IoBuffer) message;
            if (iobuffermessage.remaining() == iobuffermessage.limit()) {
                b = new byte[iobuffermessage.limit()];
                iobuffermessage.get(b);
            }
            if (new String(b) == int_req) {
                return true;
            }

        } else if (message instanceof byte[] && new String((byte[]) message).equals(int_req)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是心跳响应包，不管是对方的，还是自己的
     */
    @Override
    public boolean isResponse(IoSession session, Object message) {
        if (message instanceof IoBuffer) {
            iobuffermessage = (IoBuffer) message;
            if (iobuffermessage.remaining() == iobuffermessage.limit()) {
                b = new byte[iobuffermessage.limit()];
                iobuffermessage.get(b);
            }
            if (new String(b) == int_rep) {
                return true;
            }

        } else if (message instanceof byte[] && new String((byte[]) message).equals(int_rep)) {
            return true;
        }
        return false;
    }
}
