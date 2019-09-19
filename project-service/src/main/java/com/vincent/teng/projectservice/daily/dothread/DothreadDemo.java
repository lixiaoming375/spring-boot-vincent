package com.vincent.teng.projectservice.daily.dothread;

/**
 * @author tengxiao
 * @Description:
 * @date 2019/8/1510:11
 */
public class DothreadDemo extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
//        new DothreadDemo().start();
        // 由于 Thread继承了Runnable接口所以 可以使用下面方法启动线程
        new Thread(new DothreadDemo()).start();
    }
}
