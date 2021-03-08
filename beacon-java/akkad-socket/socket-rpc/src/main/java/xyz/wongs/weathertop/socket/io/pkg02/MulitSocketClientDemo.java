package xyz.wongs.weathertop.socket.io.pkg02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MulitSocketClientDemo {

    public static void main(String[] args) throws Exception{
        int size = 150;
        ExecutorService executorService = Executors.newFixedThreadPool(size);
        for (int i = 0; i < size; i++) {
            executorService.execute(new ClientDemo(i));
        }

        executorService.shutdown();
    }

    static class ClientDemo implements Runnable{
        private int idx;

        public ClientDemo(int idx) {
            this.idx = idx;
        }

        @Override
        public void run() {
            Socket socket = null;
            BufferedReader bufferedReader = null;
            PrintWriter printWriter = null;
            try {
                socket = new Socket("127.0.0.1",8080);
                if(null!=socket){
                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    printWriter = new PrintWriter(socket.getOutputStream(),true);
                    printWriter.println(Thread.currentThread().getName() +" -> "+ idx +" 客户端发来数据 >>>>");

                    String response = bufferedReader.readLine();
                    System.out.println(Thread.currentThread().getName() +" -> "+ idx + " The Server response "+ response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(null!=printWriter){
                    printWriter.close();
                }
                try {
                    if(null!=bufferedReader){
                        bufferedReader.close();
                    }

                    if(null!=socket)
                        socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
