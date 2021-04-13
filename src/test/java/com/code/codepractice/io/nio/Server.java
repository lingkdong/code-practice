package com.code.codepractice.io.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * {@link java.nio.channels.ServerSocketChannel}
 */
public class Server {
    private static final List<SocketChannel> socketChannels = new ArrayList<>();//保存客户端连接

    @Test
    public void start() {
        try {
            //nio 先 open 在 bind
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(9091));
            serverSocketChannel.configureBlocking(false);//设置非阻塞
            System.out.println("server start");
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();//接受连接
                //如果有客户端连接
                if (socketChannel != null) {
                    System.out.println("connection success");
                    socketChannel.configureBlocking(false);//设置非阻塞
                    socketChannels.add(socketChannel);//放入列表
                }

                //遍历列表 读取客户端发送数据
                Iterator<SocketChannel> iterator = socketChannels.iterator();
                while (iterator.hasNext()) {//空循环 浪费性能 引入 Selector多路复用器
                    SocketChannel item = iterator.next();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                    int read = item.read(byteBuffer);
                    if (read > 0) {
                        System.out.println("client data: " + new String(byteBuffer.array()));
                    } else if (read == -1) {
                        //断开连接 则移除 socketChannel
                        iterator.remove();
                        System.out.println("disconnect");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 引入多路复用器的版本
     * Selector.open()->SelectorProvider.provider()->sun.nio.ch.DefaultSelectorProvider.get()->  EPollSelectorImpl(SelectorProvider sp)-> EPoll.create()->EPoll_create EPoll.c文件中->epoll_create1(EPOLL_CLOEXEC)
     * man epoll_create1
     * 在线文档见 https://www.kernel.org/doc/man-pages/
     * https://man7.org/linux/man-pages/man2/epoll_create1.2.html
     *
     *bind->ServerSocketChannel.bind->ServerSocketChannelImpl.bind->unixBind->UnixDomainSockets.bind->UnixDomainSockets.bind0
     * ->UnixDomainSockets_bind0-> bind(fdval(env, fdo), (struct sockaddr *)&sa, sa_len);
     * man  bind
     *
     * serverSocketChannel.register->SelectableChannel.register(Selector sel, int ops)->register(Selector sel, int ops, Object att)
     * ->AbstractSelectableChannel.register->AbstractSelector.register->SelectorImpl.register(AbstractSelectableChannel var1, int var2, Object var3)->implRegister（如果选择器没关闭）
     * ->SelectionKeyImpl.interestOps: ops getAndSet VarHandle INTERESTOPS 句柄集合
     * ops :SelectionKey.OP_ACCEPT, SelectionKey.OP_READ, SelectionKey.OP_CONNECT, SelectionKey.OP_WRITE
     * ->集合中不存在 则注册事件 selector.setEventOps(this)->SelectorImpl.setEventOps->EPollSelectorImpl.setEventOps
     *
     *
     * selector.select()->SelectorImpl.select()->select(0)->lockAndDoSelect()->doSelect(long var1)
     * ->EPollSelectorImpl.doSelect(Consumer<SelectionKey> action, long timeout)->
     *     EPoll.ctl->epoll_ctl
     *
     *       int epoll_ctl(int epfd, int op, int fd, struct epoll_event *event);
     *
     *       epoll_ctl 添加 删除或 修改 fd监听事件
     *        opcodes
     *        static final int EPOLL_CTL_ADD  = 1;
     *        static final int EPOLL_CTL_DEL  = 2;
     *        static final int EPOLL_CTL_MOD  = 3;
     *          man epoll_ctl
     *           EPOLL_CTL_ADD
     *                 Register the target file descriptor fd on the epoll instance referred to by the file descriptor epfd and associate the event event with  the  internal  file
     *                 linked to fd.
     *                 1.将目标fd注册到epfd，2将事件与fd关联
     *    https://man7.org/linux/man-pages/man2/epoll_ctl.2.html
     *
     *     EPoll.wait->epoll_wait
     *
     *      int epoll_wait(int epfd, struct epoll_event *events,
     *                       int maxevents, int timeout);
     *
     *      man epoll_wait
     *      等待epfd中的事件，timeout=-1 block 阻塞,timeout=0  return immediately 立即返回结果
     *      https://man7.org/linux/man-pages/man2/epoll_wait.2.html
     */
    @Test
    public void test2() {
        try {
            ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(9091));
            serverSocketChannel.configureBlocking(false);
            //打开Selector 先open
            Selector selector=Selector.open();
            //Registers this channel with the given selector, returning a selection
            //serverSocketChannel accept 注册到Selector
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);//监听客户端连接
            System.out.println("server start");
            while (true){
                //Selects a set of keys whose corresponding channels are ready for I/O
                // This method performs a blocking
                selector.select();//blocking
                //遍历 selectionKeys
                Set<SelectionKey> selectionKeys=selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey item=iterator.next();
                    //如果是连接事件
                    if(item.isAcceptable()){
                        //客户端连接后 监听读事件
                        ServerSocketChannel _serverSocketChannel= (ServerSocketChannel) item.channel();
                        SocketChannel socketChannel=_serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ);//监听读事件
                        System.out.println("connection success");
                    }else if(item.isReadable()){
                        //有读事件 则读取数据
                        SocketChannel socketChannel= (SocketChannel) item.channel();
                        ByteBuffer byteBuffer=ByteBuffer.allocate(128);
                        int read=socketChannel.read(byteBuffer);
                        if(read>0){
                            System.out.println("client data: "+new String(byteBuffer.array()));
                        }
                    }
                    //处理完删除本次事件key
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
