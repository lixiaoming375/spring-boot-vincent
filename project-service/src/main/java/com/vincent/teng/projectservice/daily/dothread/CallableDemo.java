package com.vincent.teng.projectservice.daily.dothread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/9/1121:32
 */
public class CallableDemo implements Callable {
    @Override
    public Object call() throws Exception {
        TimeUnit.SECONDS.sleep(3);//阻塞案例演示
        return "hello world";
    }

    public static void main(String[] args) throws ExecutionException,InterruptedException {
        CallableDemo callableDemo=new CallableDemo();
        FutureTask futureTask=new FutureTask(callableDemo);
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}
