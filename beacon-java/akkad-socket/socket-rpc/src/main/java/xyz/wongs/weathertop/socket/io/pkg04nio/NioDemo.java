package xyz.wongs.weathertop.socket.io.pkg04nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioDemo {

    public void bio() throws Exception{
        File file = new File("E:\\BaiduYunDownload\\urule.sql");

        FileInputStream isr = new FileInputStream(file) ;
        byte[] bytes  = new byte[1024];
        while (isr.read()!=-1){
            isr.read(bytes);
        }
        isr.close();

    }

    public void nio() throws Exception{
        File file = new File("D:\\nio.txt");
        FileOutputStream isr = new FileOutputStream(file) ;

        FileChannel fileChannel = isr.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            String value = "I Like Java ";
            byteBuffer.put(value.getBytes());
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
        fileChannel.close();
        isr.close();

    }

    public static void main(String[] args) throws Exception {
        NioDemo nioDemo = new NioDemo();

        nioDemo.nio();

    }
}
