package xyz.wongs.weathertop.socket.io.pkg03;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HandlerExcutorPool {

    private static ThreadPoolExecutor threadPoolExecutor;

    public HandlerExcutorPool(int queues,int maxSize){
        this.threadPoolExecutor =  new ThreadPoolExecutor(1,
                maxSize,120L, TimeUnit.SECONDS, new ArrayBlockingQueue(queues),new NetWorkRejectedExecutionHandler());
    }

    public void execute(Runnable runnable){
        this.threadPoolExecutor.execute(runnable);
    }

}
