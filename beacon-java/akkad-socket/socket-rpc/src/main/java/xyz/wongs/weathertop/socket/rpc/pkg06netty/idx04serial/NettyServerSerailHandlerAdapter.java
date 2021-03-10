package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.bo.Req;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.bo.Res;

/**
 * @author WCNGS@QQ.COM
 * @ClassName ChannelHandlerAdapter$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:02$
 * @Version 1.0.0
 */
public class NettyServerSerailHandlerAdapter extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server channel active... ");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            System.out.println(" -- " + msg.toString() );
            Req req =  (Req)msg;
//            byte[] attachment = GzipUtils.ungzip(req.getAttachment());
            System.out.println("Server Body: :" + req.toString() );
            Res res = new Res();
            res.setId(req.getId());
            res.setDesc("响应内容 "+ req.getDesc());
            ctx.writeAndFlush(res);
        } finally {
            ReferenceCountUtil.release(msg) ; // 释放缓存
        }
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("读完了");
//        ctx.flush();
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
