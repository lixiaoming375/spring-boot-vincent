package com.vincent.teng.projectservice.daily.dothread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author tengxiao
 * @Description: 使用阻塞队列来实现 线程间的通信
 * @date 2019/9/522:07
 */
public class BlockingDemo {

    ArrayBlockingQueue<String> ab=new ArrayBlockingQueue<String>(10);
    {
        init();
    }

    public void init(){
        new Thread(()->{
            while(true){
                try {
                    String data=ab.take();//阻塞方式获得元素
                    System.out.println("receive:"+data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void addDate(String data)throws InterruptedException{
        ab.add(data);
        System.out.println("sendData"+data);
        TimeUnit.SECONDS.sleep(1);
    }

    public static void main(String[] args)throws  InterruptedException {
        BlockingDemo blockingDemo=new BlockingDemo();
        for(int i=0;i<10 ;i++){
            blockingDemo.addDate("data:"+i);
        }
    }


}
