package xyz.wongs.weathertop.socket.io.pkg01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerDemo {

    private static volatile ServerSocket serverSocket =null;

    public static ServerSocket getServerSocket() {

        if (null == serverSocket) {
            synchronized (SocketServerDemo.class) {
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
            while(true){
                Socket socket = serverSocket.accept();
                //异步处理
                new Thread(new ServerHandler(socket)).start();
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
