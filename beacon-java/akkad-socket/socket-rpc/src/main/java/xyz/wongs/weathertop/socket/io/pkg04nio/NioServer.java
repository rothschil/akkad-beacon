package xyz.wongs.weathertop.socket.io.pkg04nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class NioServer implements Runnable {

    private Selector selector;

    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);


    public NioServer(){}

    public NioServer(int port){
        ServerSocketChannel serverSocketChannel = null;
        try {
            this.selector = Selector.open();

            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);

            System.out.println(" NIO Server Start "+port);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            if(null!=serverSocketChannel){
//                try {
//                    serverSocketChannel.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    @Override
    public void run() {
        while(true){
            try {
                this.selector.select();
                Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
                while(keys.hasNext()){
                    SelectionKey key = keys.next();
                    keys.remove();
                    if(key.isValid()){
                        if(key.isAcceptable()){
                            this.accept(key);
                        } else if(key.isReadable()){
                            this.read(key);
                        } else if(key.isWritable()){
                            this.write(key);
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void accept(SelectionKey key){
        try {
            System.out.println("==========accept==========");
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(this.selector,SelectionKey.OP_READ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void read(SelectionKey key){
        this.readBuffer.clear();
        SocketChannel socketChannel = (SocketChannel)key.channel();
        System.out.println("==========read==========");
        try {
            int count = socketChannel.read(this.readBuffer);
            if(count==-1){
                System.out.println(" Server Accept content is null! ");
                socketChannel.close();
                key.cancel();
                return ;
            }

            this.readBuffer.flip();
            byte[] bytes = new byte[this.readBuffer.remaining()];

            this.readBuffer.get(bytes);

            String body = new String(bytes).trim();

            System.out.println(" Server Accept content is "+ body);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void write(SelectionKey key){}

    public static void main(String[] args) {
        new Thread(new NioServer(8765)).start();
    }
}
