package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx05udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NettyClient$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:12$
 * @Version 1.0.0
 */
public class NettyClientUdp {

    public static void main(String[] args) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new NettyClientUdpHandlerAdapter());
            Channel ch = b.bind(0).sync().channel();
            // 向网段内的所有机器广播UDP消息
            ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("谚语字典查询?", CharsetUtil.UTF_8),
                    new InetSocketAddress("255.255.255.255", 8765))).sync();
            if (!ch.closeFuture().await(15000)) {
                System.out.println("查询超时!");
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
