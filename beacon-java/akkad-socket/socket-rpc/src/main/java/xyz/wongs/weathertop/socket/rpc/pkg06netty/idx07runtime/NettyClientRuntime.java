package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx07runtime;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.GzipUtils;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.MarshallingCodeCFactory;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.bo.Req;

import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NettyClient$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 11:12$
 * @Version 1.0.0
 */
public class NettyClientRuntime {


    private static class SingletonHolder {
        static final NettyClientRuntime instance = new NettyClientRuntime();
    }


    public static NettyClientRuntime getInstance(){
        return SingletonHolder.instance;
    }

    private EventLoopGroup processGroup;
    private Bootstrap bootstrap;
    private ChannelFuture channelFuture;

    private NettyClientRuntime(){
        processGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(processGroup).channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                        socketChannel.pipeline().addLast(new ReadTimeoutHandler(5));
                        socketChannel.pipeline().addLast(new NettyClientRuntimeHandlerAdapter());
                    }
                });
    }

    public ChannelFuture getChannelFuture(int port){
        if(null==this.channelFuture){
            this.connect(port);
        }

        if(!this.channelFuture.channel().isActive()){
            this.connect(port);
        }
        return this.channelFuture;
    }

    public void connect(int port){
        try {
            this.channelFuture = bootstrap.connect("127.0.0.1",port).sync();
            System.out.println("远程服务器已经连接, 可以进行数据交换..");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        final NettyClientRuntime nettyClientRuntime = NettyClientRuntime.getInstance();
        int port = 8765;
        ChannelFuture cf = nettyClientRuntime.getChannelFuture(port);
        for (int i = 1; i < 3; i++) {
            Req req = new Req();
            req.setDesc("描述内容 "+ i);
            req.setId(i+"");
            System.out.println("【Client】============"+ req.toString());
            cf.channel().writeAndFlush(req);
            TimeUnit.SECONDS.sleep(3);
        }

        cf.channel().closeFuture().sync();

        System.out.println("【Client】子程序开始");

        new Thread(new Runnable() {
            @Override
            public void run() {

                ChannelFuture cfu = nettyClientRuntime.getChannelFuture(port);

                System.out.println("[Client-子程序] 是否存活: "+cfu.channel().isActive());
                System.out.println("[Client-子程序] 是否开启: "+cfu.channel().isOpen());

                Req req = new Req();
                req.setDesc("[Client-子程序]描述内容 ");
                req.setId("99");
                System.out.println("[Client-子程序] *********"+ req.toString());
                cfu.channel().writeAndFlush(req);
                try {
                    TimeUnit.SECONDS.sleep(3);
                    cfu.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println("【Client】断开连接,主线程结束..");

    }
}
