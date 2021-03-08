package xyz.wongs.weathertop.akkad.thread.reentrantlock;

public class Computer {

    public synchronized void sendMsg(){
        System.out.println(Thread.currentThread().getName() + " called sendMail()");
        sendMail();
    }

    public synchronized void sendMail(){
        System.out.println(Thread.currentThread().getName() + " ==>  called sendMsg()");
//        sendMsg();
    }
}
