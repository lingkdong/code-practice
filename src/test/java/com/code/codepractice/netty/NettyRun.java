package com.code.codepractice.netty;

import com.code.codepractice.netty.client.NettyClient;
import com.code.codepractice.netty.server.NettyServer;
import org.junit.Test;

import java.net.InetSocketAddress;

public class NettyRun {
    private static final InetSocketAddress serverPort = new InetSocketAddress("127.0.0.1", 8781);

    @Test
    public void server() {
        NettyServer server = new NettyServer();
        server.start(serverPort);//服务端启动
    }

    @Test
    public void client() {

        NettyClient client = new NettyClient();
        client.start(serverPort);//客户端去链接服务端

    }
}
