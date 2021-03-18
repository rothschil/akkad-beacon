package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx05udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NettyClientHandlerAdapter$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:15$
 * @Version 1.0.0
 */
public class NettyClientUdpHandlerAdapter extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg)
            throws Exception {
        String response = msg.content().toString(CharsetUtil.UTF_8);
        if (response.startsWith("谚语查询结果: ")) {
            System.out.println(response);
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
