package com.code.codepractice.io.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.net.InetSocketAddress;

/**
 *  NioEventLoopGroup()  默认为cpu核数的2倍
 *  详情：
 * {@link io.netty.channel.MultithreadEventLoopGroup.DEFAULT_EVENT_LOOP_THREADS}
 *   DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt(
 *                 "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
 *  NettyRuntime.availableProcessors()->若没配置属性 io.netty.availableProcessors 设置 为  Runtime.getRuntime().availableProcessors()
 *    代码：
 *    SystemPropertyUtil.getInt(
 *                                 "io.netty.availableProcessors",
 *                                 Runtime.getRuntime().availableProcessors())
 */
@Slf4j
public class NettyServer {
    public void start(InetSocketAddress socketAddress){
        //主线程组 处理连接请求
        EventLoopGroup group=new NioEventLoopGroup(1);
        //工作线程组 业务处理 由工作线程完成
        EventLoopGroup work=new NioEventLoopGroup(200);
        ServerBootstrap bootstrap=new ServerBootstrap()
                .group(group,work)
                .channel(NioServerSocketChannel.class)//服务器通道
                .childHandler(new ServerChannelInitializer())
                .localAddress(socketAddress)
                //设置服务器连接等待队列大小，服务器请求客户端连接时顺序处理
                //多个客户连接服务器时，服务器将不能处理的请求放入等待队列
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

    @Test
    public void test(){
        //>若没配置属性 io.netty.availableProcessors 设置 为  Runtime.getRuntime().availableProcessors()
        System.out.println(NettyRuntime.availableProcessors());
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println( new NioEventLoopGroup().executorCount());//默认为CPU核数2倍
    }
}
