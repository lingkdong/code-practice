package com.code.codepractice.io.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * {@link java.nio.channels.ServerSocketChannel}
 */
public class Server {
    private static final List<SocketChannel> socketChannels=new ArrayList<>();//保存客户端连接
    @Test
    public void start() {
        try {
            //nio 先 open 在 bind
            ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(9091));
            serverSocketChannel.configureBlocking(false);//设置非阻塞
            System.out.println("server start");
            while (true){
                SocketChannel socketChannel= serverSocketChannel.accept();//接受连接
                //如果有客户端连接
                if(socketChannel!=null){
                    System.out.println("connection success");
                    socketChannel.configureBlocking(false);//设置非阻塞
                    socketChannels.add(socketChannel);//放入列表
                }

                //遍历列表 读取客户端发送数据
                Iterator<SocketChannel> iterator = socketChannels.iterator();
                while (iterator.hasNext()){
                    SocketChannel item=iterator.next();
                    ByteBuffer byteBuffer=ByteBuffer.allocate(128);
                    int read=item.read(byteBuffer);
                    if(read>0){
                        System.out.println("client data: "+new String(byteBuffer.array()));
                    }else if(read==-1){
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
}
