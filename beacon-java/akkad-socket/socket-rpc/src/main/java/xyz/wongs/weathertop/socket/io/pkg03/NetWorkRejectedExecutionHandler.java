package xyz.wongs.weathertop.socket.io.pkg03;


import xyz.wongs.weathertop.socket.io.pkg01.ServerHandler;

import java.net.InetAddress;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class NetWorkRejectedExecutionHandler implements RejectedExecutionHandler {


    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (r instanceof ServerHandler) {
            ServerHandler thTk = (ServerHandler) r;
            //为了演示用，所以直接打印，勿模仿。正式场景下应该持久化或者写入日志！！！
            String address = thTk.getSocket().getInetAddress().getHostAddress();
            int port = thTk.getSocket().getPort();
            System.out.println(" Task "+ address + " : " + port +" 执行失败！");
        }
    }
}
