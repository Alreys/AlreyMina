package com.smart.xxl.mina;

import com.smart.xxl.config.Config;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import java.util.concurrent.LinkedBlockingQueue;

public class SendManager implements Runnable{
    /**
     * 管理mina的数据重发
     */
    private Config config = Config.newInstance();
    private LinkedBlockingQueue send_queue = new LinkedBlockingQueue(100);//数据待发送队列
    private static volatile SendManager sendManager;
    private ConnectManager connectManager = ConnectManager.newInstance();

    private SendManager(){

    }

    public static SendManager newInstance(){
        if (sendManager == null){
            synchronized (SendManager.class){
                if (sendManager == null){
                    sendManager = new SendManager();
                }
            }
        }
        return sendManager;
    }

    /**
     * 发送
     * */
    public void send(Object data){
        try {
            if (!send_queue.offer(data)){
                send_queue.remove(send_queue.peek());
                send_queue.put(data);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        for (;;){
            try {
                if (connectManager.ioSession != null && send_queue.peek() != null){
                    connectManager.ioSession.write(IoBuffer.wrap(send_queue.peek().toString().getBytes()));
                    send_queue.remove(send_queue.peek());
                    config.getSendListener().SendSuccess();
                }
            } catch (Exception e){
                config.getSendListener().SendFail(e);
                e.printStackTrace();
            }

        }
    }
}
