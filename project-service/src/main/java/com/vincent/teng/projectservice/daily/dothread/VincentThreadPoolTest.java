package com.vincent.teng.projectservice.daily.dothread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/9/1117:34
 */
public class VincentThreadPoolTest implements Runnable{

    private static ExecutorService es =new VincentThreadPool(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i++) {
            es.execute(new VincentThreadPoolTest());
        }
        es.shutdown();
    }
}
