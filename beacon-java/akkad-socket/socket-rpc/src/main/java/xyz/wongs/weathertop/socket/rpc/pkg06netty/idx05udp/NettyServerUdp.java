package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx05udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.MarshallingCodeCFactory;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NettyServer$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 10:29$
 * @Version 1.0.0
 */
public class NettyServerUdp {

    public static void main(String[] args) throws Exception{

        EventLoopGroup processGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(processGroup)
        .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST,true)
                .handler(new NettyServerUdpHandlerAdapter());

        ChannelFuture channelFuture = bootstrap.bind(8765).sync();
        channelFuture.channel().closeFuture().await();
        processGroup.shutdownGracefully();
    }
}
