package com.vincent.teng.projectservice.daily.dothread;

import java.util.concurrent.TimeUnit;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/8/1511:51
 */
public class DoThreadStatesDemo {

    public static void main(String[] args) {

        new Thread(()->{
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Time_Waiting_Thread").start();

        new Thread(()->{
            while(true){
                try {
                    synchronized (DoThreadStatesDemo.class){
//                        Thread.currentThread().wait();
                        DoThreadStatesDemo.class.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"waiting_Thread").start();


        new Thread(new BlockedDemo(),"BlockedDemo_001").start();
        new Thread(new BlockedDemo(),"BlockedDemo_002").start();
    }


    static class BlockedDemo extends Thread{
        @Override
        public void run() {
            synchronized (BlockedDemo.class){
                while(true){
                    try{
                        TimeUnit.SECONDS.sleep(100);
                    }catch (InterruptedException e){
                         e.printStackTrace();
                    }
                }
            }
        }
    }


}
