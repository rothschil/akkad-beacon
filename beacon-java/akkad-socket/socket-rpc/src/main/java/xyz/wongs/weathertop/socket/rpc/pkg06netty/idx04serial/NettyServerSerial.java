package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NettyServer$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 10:29$
 * @Version 1.0.0
 */
public class NettyServerSerial {

    public static void main(String[] args) throws Exception{

        EventLoopGroup processGroup = new NioEventLoopGroup();
        EventLoopGroup rwGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(processGroup,rwGroup)
        .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.SO_BACKLOG,1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                        socketChannel.pipeline().addLast(new NettyServerSerailHandlerAdapter());
                    }
                });

        ChannelFuture channelFuture = bootstrap.bind(8905).sync();

        channelFuture.channel().closeFuture().sync();
        processGroup.shutdownGracefully();
        rwGroup.shutdownGracefully();

    }
}
