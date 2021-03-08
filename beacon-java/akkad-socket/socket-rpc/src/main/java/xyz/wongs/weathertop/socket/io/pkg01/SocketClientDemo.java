package xyz.wongs.weathertop.socket.io.pkg01;

import java.io.*;
import java.net.Socket;

public class SocketClientDemo {

    public static void main(String[] args) throws Exception{
        Socket socket = null;
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        try {
            socket = new Socket("localhost",8080);
            if(null!=socket){
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printWriter = new PrintWriter(socket.getOutputStream(),true);
                printWriter.println("开始接收服务端数据");
                printWriter.println("开始接收服务端数据 >>>>");

                String response = bufferedReader.readLine();
                System.out.println(Thread.currentThread().getName() + " Server Send "+ response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null!=printWriter){
                printWriter.close();
            }

            if(null!=bufferedReader){
                bufferedReader.close();
            }

            if(null!=socket)
                socket.close();
        }
    }
}
