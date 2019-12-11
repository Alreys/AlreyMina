package com.smart.xxl.mina;

import com.smart.xxl.config.Config;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class MinaClientHandler implements IoHandler {
	private String TAG = "MinaClientHandler";
	private Config config;

	public MinaClientHandler(Config config){
		this.config = config;
	}

	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1) throws Exception {
//		System.out.println("异常捕获 session:" + arg0.getId() + ";Throwable:" + arg1.getMessage());
	}

	@Override
	public void inputClosed(IoSession arg0) throws Exception {
//		System.out.println("输入流关闭 session：" + arg0.getId());
	}

	@Override
	public void messageReceived(IoSession arg0, Object message) throws Exception {
		String data = new String((byte[])message);
		if (config.getReceiveListener() != null){
			config.getReceiveListener().ReceiverData(data);
		}
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
//		System.out.println("session:" + arg0.getId() + "发送消息：" + arg1.toString());
	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {
		if (config.getConnectListener() != null){
			config.getConnectListener().DisConnect(arg0);
		}
		if (config.isreconnect() && !ConnectManager.newInstance().closemark){
			ConnectManager.newInstance().connect();
		}
	}

	@Override
	public void sessionCreated(IoSession arg0) throws Exception {
//		System.out.println("session:" + arg0.getId() + "被创建");
	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
//		System.out.println("session:" + arg0.getId() + "当前状态" + arg1);
	}

	@Override
	public void sessionOpened(IoSession arg0) throws Exception {
//		System.out.println("session:" + arg0.getId() + "被打开");
	}

}
