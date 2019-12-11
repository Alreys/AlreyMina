# mina
 基于mina封装的Tcp工具包，支持断线重连机制，心跳机制，数据重发机制
 --
  **项目结构**  
  >-src  
  >&nbsp;&nbsp;|--com.smart.xxl   
  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|--config  
  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|--inter  
  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|--mina  
  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|--utils  
  >&nbsp;&nbsp;|--libs  
  >&nbsp;&nbsp;|--other  

代码都位于xxl包下。  
依赖位于libs包下。  
config包是mina的配置信息，如心跳时间间隔、ip地址，各种监听器的设置等等。  
inter包中的是各种监听器的接口和空实现。  
mina包为核心包，负责管理mina的创建、链接、监控、心跳机制的实现、数据重发机制的实现等等功能。  
utils包中为一些工具类，如格式转换、字符过滤器等等。  
other包是本项目的jar版本。  
  
### 使用方法
添加依赖
>将libs包和other包下的所有jar都放入项目中，你可以新建一个libs夹子来放置所有需要的依赖  

调用示例
```
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

public static void main(String[] args) {
        Config config = Config.newInstance();
        config.setIp("192.168.43.192");//设置服务器ip
        config.setPort(8080);//设置服务器端口
        config.setHeartRequest("0x11");//设置发送给服务器的心跳请求包
        config.setHeartResponse("0x12");//设置接收服务器返回的心跳响应包
        config.setkeepalive(true);//设置支持长链接
        config.setreconnect(true);//设置支持断线重连
        config.setHeartFrequency(5);//设置心跳包发送间隔单位秒
        config.setHearttimeout(3);//设置心跳超时判断时间单位秒
        config.setReconnectFrequency(5000);//设置重连的时间间隔单位毫秒
        config.setConnectListener(new ConnectListener() {//设置链接监听
            @Override
            public boolean ConnectSuccess(IoSession ioSession) {//在链接成功后执行
                Map map = new HashMap();
                map.put("message","success");
                String jsonObject = JSON.toJSONString(map);
                ioSession.write(IoBuffer.wrap(jsonObject.getBytes()));
                return false;
            }

            @Override
            public boolean ConnectFail(Exception e) {//在链接失败后执行
                Log.debug(e);
                return false;
            }

            @Override
            public boolean DisConnect(IoSession ioSession) {//在断开链接后执行
                System.out.println(ioSession.closeNow());
                return false;
            }
        });
        config.setReceiveListener(new ReceiveListener() {//设置数据接收监听
            @Override
            public boolean ReceiverData(String data) {//接收数据后执行
                System.out.println(data);
                return false;
            }

            @Override
            public boolean ReceiverFail(Error error) {//接收数据出现异常时调用
                Log.debug(error);
                return false;
            }
        });
        config.setKeepAliveListener(new KeepAliveListener() {//设置心跳机制监听器
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
        connectManager.creatConfig(config);//为链接管理传入mina的配置信息
        connectManager.connect();//开启链接

        //消息发送测试
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
```
