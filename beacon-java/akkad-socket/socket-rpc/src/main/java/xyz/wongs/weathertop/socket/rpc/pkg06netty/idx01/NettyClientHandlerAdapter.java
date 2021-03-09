package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx01;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NettyClientHandlerAdapter$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:15$
 * @Version 1.0.0
 */
public class NettyClientHandlerAdapter extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf buf = (ByteBuf) msg;

            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req, "utf-8");
            String response = "Client : 收到服务器端的返回信息：" + body;
            System.out.println("" + response );
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

}
