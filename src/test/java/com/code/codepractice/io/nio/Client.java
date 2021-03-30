package com.code.codepractice.io.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    @Test
    public void start (){
        try {
            //先 open 在connect
            SocketChannel socketChannel=SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1",9091));
            ByteBuffer byteBuffer=ByteBuffer.allocate(128);
            String msg="hello this is nio "+System.currentTimeMillis();
            byteBuffer.clear();
            byteBuffer.put(msg.getBytes());
            byteBuffer.flip();//切换buffer为读模式
            while (byteBuffer.hasRemaining()){
                socketChannel.write(byteBuffer);
            }
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void batchTest() {
        for (int i = 0; i < 10; i++) {
            Runnable runnable = () -> start();
            runnable.run();
        }
    }
}
