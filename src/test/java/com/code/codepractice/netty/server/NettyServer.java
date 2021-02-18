package com.code.codepractice.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
@Slf4j
public class NettyServer {
    public void start(InetSocketAddress socketAddress){
        //主线程
        EventLoopGroup group=new NioEventLoopGroup(1);
        //工作线程
        EventLoopGroup work=new NioEventLoopGroup(200);
        ServerBootstrap bootstrap=new ServerBootstrap()
                .group(group,work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer())
                .localAddress(socketAddress)
                .option(ChannelOption.SO_BACKLOG,1024)//设置队列大小
                .childOption(ChannelOption.SO_KEEPALIVE,true);
        try {
            ChannelFuture future=bootstrap.bind(socketAddress).sync();
            log.info("netty server start,port:{}",socketAddress.toString());
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
            work.shutdownGracefully();
        }

    }
}
