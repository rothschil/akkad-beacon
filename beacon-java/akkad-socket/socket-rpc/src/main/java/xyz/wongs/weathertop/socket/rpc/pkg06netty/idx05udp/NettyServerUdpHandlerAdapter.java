package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx05udp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import xyz.wongs.drunkard.base.utils.StringUtils;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.GzipUtils;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.bo.Req;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.bo.Res;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author WCNGS@QQ.COM
 * @ClassName ChannelHandlerAdapter$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:02$
 * @Version 1.0.0
 */
public class NettyServerUdpHandlerAdapter extends SimpleChannelInboundHandler<DatagramPacket> {

    // 谚语列表
    private static final String[] DICTIONARY = {
            "只要功夫深，铁棒磨成针。",
            "旧时王谢堂前燕，飞入寻常百姓家。",
            "洛阳亲友如相问，一片冰心在玉壶。",
            "一寸光阴一寸金，寸金难买寸光阴。",
            "老骥伏枥，志在千里。烈士暮年，壮心不已!"
    };

    private String nextQuote() {
        int quoteId = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
        return DICTIONARY[quoteId];
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, DatagramPacket packet)
            throws Exception {
        String req = packet.content().toString(CharsetUtil.UTF_8);
        System.out.println(req);
        if ("谚语字典查询?".equals(req)) {
            ctx.writeAndFlush(
                    new DatagramPacket(Unpooled.copiedBuffer("谚语查询结果: " + nextQuote(),
                            CharsetUtil.UTF_8), packet.sender()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        cause.printStackTrace();
    }

}
