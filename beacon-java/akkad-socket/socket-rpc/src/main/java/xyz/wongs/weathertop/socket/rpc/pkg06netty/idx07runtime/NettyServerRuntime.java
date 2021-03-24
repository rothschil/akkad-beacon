package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx07runtime;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.experimental.PackagePrivate;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.MarshallingCodeCFactory;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NettyServer$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 10:29$
 * @Version 1.0.0
 */
public class NettyServerRuntime {

    private EventLoopGroup processGroup;
    private EventLoopGroup rwGroup;
    private ServerBootstrap bootstrap;
    private ChannelFuture channelFuture;

    private static class SingletonHolder{
        static final NettyServerRuntime instance = new NettyServerRuntime();
    }

    public static NettyServerRuntime getInstance(){
        return SingletonHolder.instance;
    }


    private NettyServerRuntime(){
        processGroup = new NioEventLoopGroup();
        rwGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        buildBootstrap();
    }

    public void buildBootstrap(){
        bootstrap.group(processGroup,rwGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                        socketChannel.pipeline().addLast(new ReadTimeoutHandler(5));
                        socketChannel.pipeline().addLast(new NettyServerRuntimeHandlerAdapter());
                    }
                });
    }

    public void connect(int port){
        try {
            channelFuture = bootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ChannelFuture getChannelFuture(int port){
        if(null==channelFuture){
            this.connect(port);
        }
        if(!channelFuture.channel().isActive()){
            this.connect(port);
        }
        return this.channelFuture;
    }

    public void close(){
        try {
            this.channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        NettyServerRuntime nettyServerRuntime = NettyServerRuntime.getInstance();
        int port = 8765;
        nettyServerRuntime.getChannelFuture(port);
        nettyServerRuntime.close();
    }
}
