package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx01;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NettyClient$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:12$
 * @Version 1.0.0
 */
public class NettyClient {

    public static void main(String[] args) throws Exception{
        EventLoopGroup processGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(processGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new NettyClientHandlerAdapter());
            }
        });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8900);

        channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty".getBytes()));


        channelFuture.channel().closeFuture().sync();
        processGroup.shutdownGracefully();
    }
}
