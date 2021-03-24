package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx07runtime;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.bo.Req;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.bo.Res;

import java.util.concurrent.TimeUnit;

/**
 * @author WCNGS@QQ.COM
 * @ClassName ChannelHandlerAdapter$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:02$
 * @Version 1.0.0
 */
public class NettyServerRuntimeHandlerAdapter extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            Req req =  (Req)msg;
            System.out.println("【Server】 "+req.toString());
            Res res = new Res();
            res.setId(req.getId());
            res.setDesc("响应内容 "+ req.getDesc());
            ctx.writeAndFlush(res);
        } finally {
            ReferenceCountUtil.release(msg) ; // 释放缓存
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
