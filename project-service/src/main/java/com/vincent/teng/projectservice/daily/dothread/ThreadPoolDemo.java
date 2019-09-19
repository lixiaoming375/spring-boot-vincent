package com.vincent.teng.projectservice.daily.dothread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/9/1111:09
 */
public class ThreadPoolDemo implements Runnable{

    static ExecutorService service= Executors.newFixedThreadPool(3);

    @Override
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        for(int i=0;i<100;i++){
            service.execute(new ThreadPoolDemo());
        }
        service.shutdown();
    }
}
