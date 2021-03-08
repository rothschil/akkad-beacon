package xyz.wongs.weathertop.socket.io.pkg05aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

public class OwnCompletionHandler implements CompletionHandler<AsynchronousSocketChannel,AioServer> {


    @Override
    public void completed(AsynchronousSocketChannel channel, AioServer aioServer) {
        aioServer.assc.accept(aioServer,this);
        read(channel);
    }

    @Override
    public void failed(Throwable exc, AioServer aioServer) {
        exc.printStackTrace();
    }

    public void read (final AsynchronousSocketChannel channel){
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        channel.read(byteBuffer,byteBuffer ,new CompletionHandler<Integer,ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.flip();
                System.out.println(" 收到数据字节长度 " + result );
                String contact = new String(attachment.array()).trim();
                System.out.println(" 收到数据内容 " + contact );
                String response = " 服务器响应客户端 : " + contact;
                System.out.println(response);
                write(channel,response);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });
    }

    public void write(final AsynchronousSocketChannel channel,String response){
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(response.getBytes());
            byteBuffer.flip();
            channel.write(byteBuffer).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
