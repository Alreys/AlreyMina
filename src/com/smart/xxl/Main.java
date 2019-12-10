package com.smart.xxl;

import com.alibaba.fastjson.JSON;
import com.oracle.tools.packager.Log;
import com.smart.xxl.config.Config;
import com.smart.xxl.inter.ConnectListener;
import com.smart.xxl.inter.KeepAliveListener;
import com.smart.xxl.inter.ReceiveListener;
import com.smart.xxl.mina.ConnectManager;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Config config = Config.newInstance();
        config.setIp("192.168.43.192");
        config.setPort(8080);
        config.setHeartRequest("0x11");
        config.setHeartResponse("0x12");
        config.setkeepalive(true);
        config.setreconnect(true);
        config.setHeartFrequency(5);
        config.setHearttimeout(3);
        config.setReconnectFrequency(5000);
        config.setConnectListener(new ConnectListener() {
            @Override
            public boolean ConnectSuccess(IoSession ioSession) {
                Map map = new HashMap();
                map.put("message","success");
                String jsonObject = JSON.toJSONString(map);
                ioSession.write(IoBuffer.wrap(jsonObject.getBytes()));
                return false;
            }

            @Override
            public boolean ConnectFail(Exception e) {
                Log.debug(e);
                return false;
            }

            @Override
            public boolean DisConnect(IoSession ioSession) {
                System.out.println(ioSession.closeNow());
                return false;
            }
        });
        config.setReceiveListener(new ReceiveListener() {
            @Override
            public boolean ReceiverData(String data) {
                System.out.println(data);
                return false;
            }

            @Override
            public boolean ReceiverFail(Error error) {
                Log.debug(error);
                return false;
            }
        });
        config.setKeepAliveListener(new KeepAliveListener() {
            @Override
            public void BeforeSendHeartbeat(IoSession arg0) {

            }

            @Override
            public void AfterReceiverHeartbeat(IoSession arg0, Object arg1) {

            }

            @Override
            public void HeartbeatTimeOut(KeepAliveFilter arg0, IoSession arg1) {

            }
        });
        final ConnectManager connectManager = ConnectManager.newInstance();
        connectManager.creatConfig(config);
        connectManager.connect();

        new Thread(){
            @Override
            public void run() {
                for (int i = 0;; i ++){
                    try {
                        Thread.sleep(1000);
                        connectManager.sendmessage("ssssss" + i);
                        System.out.println("ssssss" + i);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }
}
