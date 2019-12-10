package com.smart.xxl.mina;

import com.smart.xxl.config.Config;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

/**处理长连接超时的业务*/
public class MinaKeepAliveRequestTimeoutHandler implements KeepAliveRequestTimeoutHandler{
	private Config config;

	public MinaKeepAliveRequestTimeoutHandler(Config config){
		this.config = config;
	}
	@Override
	public void keepAliveRequestTimedOut(KeepAliveFilter arg0, IoSession arg1) throws Exception {
		arg1.closeNow();
		if (config.getKeepAliveListener() != null)
		config.getKeepAliveListener().HeartbeatTimeOut(arg0,arg1);
	}
}
