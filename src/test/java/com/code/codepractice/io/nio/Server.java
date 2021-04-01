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
