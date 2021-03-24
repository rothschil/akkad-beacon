package xyz.wongs.weathertop.socket.rpc.pkg06netty.idx08download;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx04serial.MarshallingCodeCFactory;
import xyz.wongs.weathertop.socket.rpc.pkg06netty.idx07runtime.NettyServerRuntimeHandlerAdapter;

/**
 * @author WCNGS@QQ.COM
 * @ClassName NettyServer$
 * @Description
 * @Github <a>https://github.com/rothschil</a>
 * @date 21/3/9$ 10:29$
 * @Version 1.0.0
 */
public class ServerDownload {

    private static final String DEFAULT_URL = "/Doc/";

    private EventLoopGroup processGroup;
    private EventLoopGroup rwGroup;
    private ServerBootstrap bootstrap;
    private ChannelFuture channelFuture;

    public static ServerDownload getInstance(final String url){
        return new ServerDownload(url);
    }

    private ServerDownload(final String url){
        processGroup = new NioEventLoopGroup();
        rwGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        buildBootstrap(url);
    }

    public void buildBootstrap(final String url){
        bootstrap.group(processGroup,rwGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 1、http的解码器
                        socketChannel.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                        // 2、加入ObjectAggregator解码器，作用是他会把多个消息转换为单一的FullHttpRequest或者FullHttpResponse
                        socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                        // 3、http的编码器
                        socketChannel.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                        // 4、加入chunked 主要作用是支持异步发送的码流（大文件传输），但不专用过多的内存，防止java内存溢出
                        socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                        // 3、http的编码器
                        socketChannel.pipeline().addLast("fileServerHandle",new ServerDownloadHandler(url));
                    }
                });
    }

    public void connect(int port){
        try {
            channelFuture = bootstrap.bind("192.168.60.82",port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.processGroup.shutdownGracefully();
            this.rwGroup.shutdownGracefully();
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

    public static void main(String[] args) throws Exception{
        String url = DEFAULT_URL;
        ServerDownload serverDownload = ServerDownload.getInstance(url);
        int port = 9099;
        serverDownload.getChannelFuture(port);
    }
}
