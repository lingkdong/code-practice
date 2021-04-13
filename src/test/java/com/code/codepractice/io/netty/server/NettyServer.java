package com.code.codepractice.io.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.NettyRuntime;
import io.netty.util.internal.PlatformDependent;
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
 *
 * bind
 * bootstrap.bind->doBind
 * 1.initAndRegister()->ChannelFactory<T extends Channel>.T newChannel->constructor.newInstance()->new NioServerSocketChannel()
 * ->  super(null, channel, SelectionKey.OP_ACCEPT)->  ch.configureBlocking(false);
 *  封装了 NIO
 *  doBind0()->channel.bind->DefaultChannelPipeline.bind(SocketAddress localAddress, ChannelPromise promise)-> next.invokeBind->  ((ChannelOutboundHandler) handler()).bind
 *  ->DefaultChannelPipeline.bind(
 *                 ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise)-> unsafe.bind->AbstractChannel.bind->doBind->NioServerSocketChannel.doBind
 *  ->ServerSocketChannelImpl.bind
 *   封装了NIO后续 见 {@link com.code.codepractice.io.nio.Server 中注释}
 *   对NIO做了优化 使用了很多异步程序
 *
 * register
 *  2.initAndRegister-> config().group().register(channel)->MultithreadEventLoopGroup.register->SingleThreadEventLoop.register->   promise.channel().unsafe().register
 *  ->AbstractChannel.register(EventLoop eventLoop, final ChannelPromise promise)->register0->doRegister
 *  ->AbstractNioChannel.doRegister->javaChannel().register(eventLoop().unwrappedSelector(), 0, this);
 *  ->SelectableChannel.register->AbstractSelectableChannel.register
 *  封装了NIO后续 见 {@link com.code.codepractice.io.nio.Server 中注释}
 *
 * select
 * 3.AbstractChannel.register->eventLoop.execute->SingleThreadEventLoop.execute->startThread()->doStartThread
 *  ->SingleThreadEventExecutor.this.run()->NioEventLoop.run->select(curDeadlineNanos)-> selector.select()
 *  ->SelectedSelectionKeySetSelector.select->delegate.select()->SelectorImpl.select
 *  封装了NIO后续 见 {@link com.code.codepractice.io.nio.Server 中注释}
 *
 *  interestOps
 *  4.AbstractChannel.register->register0->beginRead()-> doBeginRead()->AbstractNioChannel.doBeginRead-> selectionKey.interestOps
 *  ->SelectionKeyImpl.interestOps
 *  封装了NIO后续 见 {@link com.code.codepractice.io.nio.Server 中注释}
 *
 *  read
 *  5.SingleThreadEventExecutor.this.run()->processSelectedKeys->processSelectedKeysOptimized->
 *   if ((readyOps & (SelectionKey.OP_READ | SelectionKey.OP_ACCEPT)) != 0 || readyOps == 0) {
 *   unsafe.read()
 *   }
 *  5.1 建立accept ->AbstractNioMessageChannel.read->doReadMessages(readBuf)->NioServerSocketChannel.doReadMessages- >SocketUtils.accept 建立accept连接
 *  5.2 读数据->AbstractNioByteChannel.read->doReadBytes(byteBuf)->NioSocketChannel.doReadBytes
 *  ->byteBuf.writeBytes数据写到缓存区->pipeline.fireChannelRead(byteBuf)->ChannelPipeline.fireChannelRead 回调pipeline
 *  -> ((ChannelInboundHandler) handler()).channelRead->NettyServerHandler.channelRead调用自定义的 channelRead
 *  pipeline 职责链模式 next.invokeChannelRead
 */
@Slf4j
public class NettyServer {
    public void start(InetSocketAddress socketAddress){
        //主线程组 处理连接请求 NioEventLoopGroup 包了 selector
        EventLoopGroup group=new NioEventLoopGroup(1);
        //工作线程组 业务处理 由工作线程完成
        EventLoopGroup work=new NioEventLoopGroup(200);
        ServerBootstrap bootstrap=new ServerBootstrap()
                .group(group,work)
                .channel(NioServerSocketChannel.class)//服务器通道
                .childHandler(new ServerChannelInitializer())//childHandler
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
        System.out.println(PlatformDependent.javaVersion());
        System.out.println(System.getProperty("java.specification.version"));//获取jdk版本
    }
}
