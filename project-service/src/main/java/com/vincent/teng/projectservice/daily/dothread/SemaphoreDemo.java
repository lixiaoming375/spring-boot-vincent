package com.vincent.teng.projectservice.daily.dothread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/9/517:51
 */
public class SemaphoreDemo {


    public static void main(String[] args) {
        Semaphore semaphore=new Semaphore(5);
        for (int i=0;i<10;i++){
            new Car(i,semaphore).start();
        }
    }



    static  class Car extends Thread{
        private int num;
        private Semaphore semaphore;

        public  Car(int num,Semaphore semaphore){
            this.num=num;
            this.semaphore=semaphore;
        }

        @Override
        public void run() {

            try {
                semaphore.acquire();//获取一个许可
                System.out.println("第"+num+"占用一个停车位");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("第"+num+"辆车离开");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
