package xyz.wongs.weathertop.socket.io.pkg05aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

public class AioClient implements Runnable{

    AsynchronousSocketChannel asc ;

    public AioClient(){
        try {
            asc = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect (){
        asc.connect(new InetSocketAddress("127.0.0.1",8080));
    }

    public void write(String content){
        try {
            asc.write(ByteBuffer.wrap(content.getBytes())).get();
            read();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void read(){
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            asc.read(buffer).get();
            buffer.flip();

            byte[] bytes = new byte[buffer.remaining()];

            buffer.get(bytes);
            System.out.println("Thread Name is "+ Thread.currentThread().getName() +" => "+ new String(bytes,"utf-8").trim());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {
        while(true){

        }
    }

    public static void main(String[] args) {

        AioClient aioClient1 = new AioClient();
        AioClient aioClient2 = new AioClient();
        AioClient aioClient3 = new AioClient();
        aioClient1.connect();
        aioClient2.connect();
        aioClient3.connect();

        new Thread(aioClient1,"aioClient1").start();
        new Thread(aioClient1,"aioClient2").start();
        new Thread(aioClient1,"aioClient3").start();

        aioClient1.write(" I `m aioClient1");
        aioClient2.write(" I `m aioClient2");
        aioClient3.write(" I `m aioClient3");

    }

}
