package com.vincent.teng.projectservice.daily.dothread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/8/2814:08
 */
public class ConditionDemo  {

    public static void main(String[] args) {
        Lock lock=new ReentrantLock();
        Condition condition=lock.newCondition();

        new Thread(()->{
            try {
                lock.lock();//竞争锁
                System.out.println(" begin -》conditionWait");
                condition.await();//阻塞
                System.out.println(" end -》conditionWait");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();//释放锁
            }
        }).start();


        new Thread(()->{
            try{
                lock.lock();//竞争锁
                System.out.println("begin ->conditionNotify");
                condition.signal();//唤醒阻塞状态的线程
                System.out.println("end ->conditionNotify");
            } finally {
                lock.unlock();//释放锁
            }
        }).start();

    }


}
