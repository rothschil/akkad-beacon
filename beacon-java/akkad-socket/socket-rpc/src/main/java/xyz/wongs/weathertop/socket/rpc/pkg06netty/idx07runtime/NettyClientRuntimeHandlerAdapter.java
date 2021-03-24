package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx07runtime;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.bo.Res;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NettyClientHandlerAdapter$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:15$
 * @Version 1.0.0
 */
public class NettyClientRuntimeHandlerAdapter extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            Res res = (Res)msg;
            System.out.println("【Client】收到Server响应 "+ res.toString() );
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
