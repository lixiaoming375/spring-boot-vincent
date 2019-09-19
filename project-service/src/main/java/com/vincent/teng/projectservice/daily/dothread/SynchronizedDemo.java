package com.vincent.teng.projectservice.daily.dothread;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/8/2216:17
 */
public class SynchronizedDemo {

    public synchronized void funcation1(){//获取同步互斥锁
        System.out.println("funcation1");
        funcation2();
    }
    public  void funcation2(){
        synchronized (this){//等待获取同步互斥锁
            System.out.println("funcation2");
        }
    }
    public static void main(String[] args) {
        new SynchronizedDemo().funcation1();
    }
}
