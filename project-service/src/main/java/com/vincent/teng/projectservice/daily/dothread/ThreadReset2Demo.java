package com.vincent.teng.projectservice.daily.dothread;

import java.util.concurrent.TimeUnit;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/8/169:49
 */
public class ThreadReset2Demo {
    private static int i;
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {//默认是false
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println("demo");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("i:" + i);
        });
        thread.start();

        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();//改成true

        System.out.println(thread.isInterrupted());
    }
}
