package xyz.wongs.weathertop.socket.io.pkg03;


import xyz.wongs.weathertop.socket.io.pkg01.ServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPoolSocketServerDemo {

    private static volatile ServerSocket serverSocket =null;

    public static ServerSocket getServerSocket() {

        if (null == serverSocket) {
            synchronized (ThreadPoolSocketServerDemo.class) {
                if (null == serverSocket) {
                    try {
                        serverSocket = new ServerSocket(8080);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return serverSocket;
    }

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = null;
        try {
            serverSocket = getServerSocket();
            System.out.println(" Server Start .. ");
            // 自定义线程池，实现线程资源可控！
            HandlerExcutorPool handlerExcutorPool = new HandlerExcutorPool(5,2);
            while(true){
                Socket socket = serverSocket.accept();
                //提交任务
                handlerExcutorPool.execute(new ServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null!=serverSocket)
                serverSocket.close();

            serverSocket = null;
        }
    }
}
