package com.code.codepractice.io.netty.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ClientChannelInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline=channel.pipeline();
       /* pipeline.addLast(new HttpServerCodec());//netty http编码和解码器
        pipeline.addLast(new ChunkedWriteHandler());//大文件分区传输
        //完全解析Http消息体
        pipeline.addLast(new HttpObjectAggregator(64*1024));
        pipeline.addLast(new WebSocketServerCompressionHandler());//webSocket压缩数据
        //websocket监听 uri ws://localhost:port/ws   协议长度限制
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws",null,true,10*1024));
        //链接60秒没接收到 消息 触发 IdleStateHandler 可以监听该该事件3 做相应处理
        pipeline.addLast(new IdleStateHandler(60, 0, 0, TimeUnit.SECONDS));
*/
        //自定义handler
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast(new NettyClientHandler());

    }
}
