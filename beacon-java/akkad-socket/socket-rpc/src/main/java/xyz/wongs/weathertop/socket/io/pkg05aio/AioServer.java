package xyz.wongs.weathertop.socket.io.pkg05aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AioServer {

    private ExecutorService executorService ;

    private AsynchronousChannelGroup acg ;

    public AsynchronousServerSocketChannel assc;

    public AioServer(int port) {
        try {
            //1、自定义线程池
            executorService = Executors.newCachedThreadPool();
            //2、创建线程组
            acg = AsynchronousChannelGroup.withCachedThreadPool(executorService,1);
            //3、新创建的异步服务器套接字通道已打开，但尚未绑定
            assc = AsynchronousServerSocketChannel.open();
            //4、可以绑定到本地地址，并可以通过调用bind方法配置为侦听连接。
            assc.bind(new InetSocketAddress(port));
            //5、 绑定后，accept方法用于启动对通道套接字的连接的接受。但尝试在未绑定的通道上调用accept方法将导致引发NotYetBoundException。
            assc.accept(this,new OwnCompletionHandler());
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AioServer(8080);
    }
}
