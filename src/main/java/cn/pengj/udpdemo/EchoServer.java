/*
 * Copyright (C) 2016 即时通讯网(52im.net) The MobileIMSDK Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/MobileIMSDK
 *  
 * 即时通讯网(52im.net) - 即时通讯技术社区! PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms.
 */
package cn.pengj.udpdemo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class EchoServer
{
	public static void main(String[] args) throws InterruptedException
	{
		Bootstrap b = new Bootstrap();
		EventLoopGroup group = new NioEventLoopGroup();
		b.group(group)
			.channel(NioDatagramChannel.class)
//			.option(ChannelOption.SO_BROADCAST, true)
			.handler(new EchoSeverHandler());
		
		// 服务端监听在9999端口
		b.bind(9999).sync().channel().closeFuture().await();
	}
}
