package xyz.wongs.weathertop.akkad.thread.reentrantlock;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.Test;

public class TestReentrantlock {

//
//    @Test
//    public void testReentrantLock(){
//
//
//    }

    public static void main(String[] args) {
        Computer computer  = new Computer();

        new Thread(()->{
            computer.sendMsg();
        },"Thread_1").start();

        new Thread(()->{
            computer.sendMail();
        },"Thread_2").start();
    }


}

