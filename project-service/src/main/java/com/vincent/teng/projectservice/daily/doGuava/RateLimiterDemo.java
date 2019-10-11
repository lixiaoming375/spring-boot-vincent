package com.vincent.teng.projectservice.daily.doGuava;

import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author tengxiao
 * @Description:  rateLimit 实现令牌桶算法
 * @date 2019/9/2318:55
 */
public class RateLimiterDemo {

    //限速器：速率是每秒生成10个许可
    RateLimiter  rateLimiter=RateLimiter.create(5) ;

    public void doPay(){
        //获取一个许可，阻塞
        rateLimiter.acquire();
        System.out.println(Thread.currentThread().getName()+"开始执行支付");
    }

    public  void doPay1(){
        // 尝试去获取一个令牌 非堵塞地尝试获取许可，如果获取到则返回true，否则返回false
        if(rateLimiter.tryAcquire()){
            System.out.println(Thread.currentThread().getName()+"开始执行支付");
        }else{
            System.out.println("系统繁忙");
        }
    }


    public static void main(String[] args) throws IOException {
        RateLimiterDemo rateLimiterDemo=new RateLimiterDemo();
        CountDownLatch countDownLatch=new CountDownLatch(1);
         for (int i=0;i<20;i++){
             new Thread(()->{
                 try {
                     countDownLatch.await();
                     Thread.sleep(1000);
                     rateLimiterDemo.doPay();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }).start();
         }
         countDownLatch.countDown();
         System.in.read();
    }
}
