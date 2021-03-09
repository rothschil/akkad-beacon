package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx02;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NettyClient$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:12$
 * @Version 1.0.0
 */
public class NettyClientDelimit {

    public static void main(String[] args) throws Exception{
        EventLoopGroup processGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(processGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ByteBuf byteBuf = Unpooled.copiedBuffer("$_".getBytes());
                socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,byteBuf));
                socketChannel.pipeline().addLast(new StringDecoder());
                socketChannel.pipeline().addLast(new NettyClientDelimtHandlerAdapter());
            }
        });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8905);

        channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("aa$_".getBytes()));
        channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("bbbbb$_".getBytes()));
        channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("ccccccc$_".getBytes()));

        channelFuture.channel().closeFuture().sync();
        processGroup.shutdownGracefully();
    }
}
