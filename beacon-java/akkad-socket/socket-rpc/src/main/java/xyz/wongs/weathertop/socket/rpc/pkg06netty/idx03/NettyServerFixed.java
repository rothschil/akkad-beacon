package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx03;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NettyServer$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 10:29$
 * @Version 1.0.0
 */
public class NettyServerFixed {

    public static void main(String[] args) throws Exception{

        // 1、 创建线程组（2个），一个用于 处理请求，一个用于 网络读写Group
        EventLoopGroup processGroup = new NioEventLoopGroup();
        EventLoopGroup rwGroup = new NioEventLoopGroup();

        // 2、配置辅助工具类，用于设置基本信息
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 2.1 绑定两个线程组
        bootstrap.group(processGroup,rwGroup)
        // 2.2 指定NIO的模式
        .channel(NioServerSocketChannel.class)
                // 2.3 设置tcp缓冲区大小
                .option(ChannelOption.SO_BACKLOG,1024)
                // 2.4 设置发送缓冲大小
                .option(ChannelOption.SO_SNDBUF,32*1024)
                // 2.5 设置接收缓冲大小
                .option(ChannelOption.SO_RCVBUF,32*1024)
                // 3、设置数据接收方法处理
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(4));
                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new NettyServerFixedHandlerAdapter());
                    }
                });

        //4 进行绑定
        ChannelFuture channelFuture = bootstrap.bind(8905).sync();

        //5、关闭
        channelFuture.channel().closeFuture().sync();

        //6、等待关闭
        processGroup.shutdownGracefully();
        rwGroup.shutdownGracefully();

    }
}
