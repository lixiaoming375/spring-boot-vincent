package com.vincent.teng.projectservice.daily.dothread;

import java.util.concurrent.TimeUnit;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/8/169:49
 */
public class ThreadResetDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("before:" + Thread.currentThread().isInterrupted());
                    Thread.interrupted();//将 _isinterrupted   复位设置成false
                    System.out.println("after:" + Thread.currentThread().isInterrupted());
                }
            }

        });
        thread.start();

        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();//改成true
    }
}
