package com.smart.xxl.utils;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;


public class TextLineCodecFactory implements ProtocolCodecFactory{
	@Override
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		//解码器
		ProtocolDecoder protocolDecoder = new ProtocolDecoder() {

			@Override
			public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			public void dispose(IoSession arg0) throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			public void decode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput decodeoutput) throws Exception {
				int limt = buffer.limit();
				byte[] bytes = new byte[limt];
				buffer.get(bytes);
				decodeoutput.write(bytes);

			}
		};
		return protocolDecoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		//编码器
		ProtocolEncoder protocolEncoder = new ProtocolEncoder() {

			@Override
			public void encode(IoSession session, Object message, ProtocolEncoderOutput encodeoutput) throws Exception {
				// TODO Auto-generated method stub
				encodeoutput.write(message);
				encodeoutput.flush();

			}

			@Override
			public void dispose(IoSession arg0) throws Exception {
				// TODO Auto-generated method stub

			}
		};
		return protocolEncoder;
	}

}
