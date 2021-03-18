package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.bo.Req;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.bo.Res;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NettyClientHandlerAdapter$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:15$
 * @Version 1.0.0
 */
public class NettyClientSerailHandlerAdapter extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String path = System.getProperty("user.dir") + File.separatorChar +"Doc"  + File.separatorChar + "IMG_2859.jpg";
        File file = new File(path);
        try {
            for (int i = 1; i < 5; i++) {
                Req req = new Req();
                req.setDesc("描述内容One "+ i);
                req.setId(i+"");

                FileInputStream in = new FileInputStream(file);
                byte[] data = new byte[in.available()];
                in.read(data);
                req.setAttachment(GzipUtils.gzip(data));
                in.close();

                ctx.writeAndFlush(req);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            Res res = (Res)msg;
            System.out.println("收到Server响应 "+ res.toString() );
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
