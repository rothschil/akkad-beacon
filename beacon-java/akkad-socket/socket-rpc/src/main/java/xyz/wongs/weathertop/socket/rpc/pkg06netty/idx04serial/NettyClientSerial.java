package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.bo.Req;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NettyClient$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:12$
 * @Version 1.0.0
 */
public class NettyClientSerial {

    public static void main(String[] args) throws Exception{
        EventLoopGroup processGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(processGroup).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(20*1024,0,2));
                socketChannel.pipeline().addLast(new NettyClientSerailHandlerAdapter());
            }
        });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8905);

/*        for (int i = 1; i < 5; i++) {
            Req req = new Req();
            req.setId(""+i);
            req.setDesc("描述内容 "+i);
            System.out.println(req.toString());
            channelFuture.channel().writeAndFlush(req);
        }*/


        channelFuture.channel().closeFuture().sync();
        processGroup.shutdownGracefully();
    }
}
