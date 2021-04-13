package com.code.codepractice.io.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

@Slf4j
public class NettyClient {
    public void start(InetSocketAddress socketAddress){
        EventLoopGroup group=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap()
                .group(group)
                .option(ChannelOption.TCP_NODELAY,true)
                .channel(NioSocketChannel.class)
                .handler(new ClientChannelInitializer());
        try {
            ChannelFuture future=bootstrap.connect(socketAddress).sync();
            log.info("client connect success");
            future.channel().writeAndFlush("hello,this is client test send");
          /*  Scanner scanner=new Scanner(System.in);//扫描输入
            while (scanner.hasNextLine()){
                future.channel().writeAndFlush(scanner.nextLine());
            }*/
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
